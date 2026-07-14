package com.efitops.basesetup.controller;

import java.lang.reflect.*;
import java.util.*;

import javax.persistence.*;

import org.reflections.Reflections;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screen")
public class DynamicScreenController {

    @GetMapping("/full/{screenCode}")
    public Map<String, Object> getFullScreen(@PathVariable String screenCode) {

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> paramObject = new HashMap<>();

        try {

            Reflections reflections =
                    new Reflections("com.efitops.basesetup.entity");

            Set<Class<?>> entities =
                    reflections.getTypesAnnotatedWith(Entity.class);

            for (Class<?> parentClass : entities) {

                try {

                    Object obj =
                        parentClass.getDeclaredConstructor().newInstance();

                    Field screenField =
                        parentClass.getDeclaredField("screenCode");

                    screenField.setAccessible(true);

                    Object val = screenField.get(obj);

                    if (val != null &&
                        val.toString().equalsIgnoreCase(screenCode)) {

                        response.put("status", true);
                        response.put("message", "Success");

                        paramObject.put("entityName",
                                parentClass.getSimpleName());

                        paramObject.put("tableName",
                          parentClass.getAnnotation(Table.class).name());

                        // Parent fields
                        List<String> parentFields = new ArrayList<>();

                        for (Field f :
                                parentClass.getDeclaredFields()) {

                            if (!f.isAnnotationPresent(OneToMany.class)) {
                                parentFields.add(f.getName());
                            }
                        }

                        paramObject.put("parentFields", parentFields);

                        // Child tables
                        List<Object> childTables =
                                new ArrayList<>();

                        for (Field field :
                                parentClass.getDeclaredFields()) {

                            if (field.isAnnotationPresent(
                                    OneToMany.class)) {

                                ParameterizedType type =
                                  (ParameterizedType)
                                   field.getGenericType();

                                Class<?> childClass =
                                  (Class<?>) type
                                   .getActualTypeArguments()[0];

                                Map<String, Object> child =
                                        new HashMap<>();

                                child.put("entityName",
                                    childClass.getSimpleName());

                                if (childClass.isAnnotationPresent(
                                        Table.class)) {

                                    child.put("tableName",
                                      childClass
                                      .getAnnotation(Table.class)
                                      .name());
                                }

                                List<String> childFields =
                                        new ArrayList<>();

                                for (Field cf :
                                      childClass
                                      .getDeclaredFields()) {

                                    if (!cf.isAnnotationPresent(
                                            ManyToOne.class)) {

                                        childFields.add(
                                            cf.getName());
                                    }
                                }

                                child.put("fields",
                                        childFields);

                                childTables.add(child);
                            }
                        }

                        paramObject.put("childTables", childTables);

                        response.put("paramObject", paramObject);

                        return response;
                    }

                } catch (Exception e) {
                }
            }

            response.put("status", false);
            response.put("message", "Screen not found");

        } catch (Exception e) {
            response.put("status", false);
            response.put("message", e.getMessage());
        }

        return response;
    }
}