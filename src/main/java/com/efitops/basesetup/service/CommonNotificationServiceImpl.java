package com.efitops.basesetup.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.NotificationDesignationDetailsVO;
import com.efitops.basesetup.entity.NotificationDesignationVO;
import com.efitops.basesetup.entity.NotificationVO;
import com.efitops.basesetup.repo.EmployeeMasterRepo;
import com.efitops.basesetup.repo.NotificationDesignationDetailsRepo;
import com.efitops.basesetup.repo.NotificationRepo;
import com.efitops.basesetup.repo.UserRepo;


@Service
public class CommonNotificationServiceImpl implements CommonNotificationService{

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonNotificationServiceImpl.class);
	
	@Autowired
	NotificationRepo notificationRepo;
	
	@Autowired
	NotificationDesignationDetailsRepo notificationDesignationDetailsRepo;
	
	@Autowired
	EmployeeMasterRepo employeeMasterRepo;
	
	@Autowired
	UserRepo userRepo;
	
//	public void generateNotification(
//	        String screenCode,
//	        Long id,
//	        Object oldObj,
//	        Object newObj) {
//
//	    // 👉 1. Get config
//	    NotificationDesignationDetailsVO config =
//	            notificationDesignationDetailsRepo.findByScreenCode(screenCode);
//
//	    if (config == null) {
//	        throw new RuntimeException("No config for screen: " + screenCode);
//	    }
//
//	    // 👉 2. Decide action
//	    String action = (oldObj == null) ? "CREATE" : "UPDATE";
//
//	    // 👉 3. Generate message
//	    String msg = generateMessage(config, oldObj, newObj, action);
//
//	    // 👉 4. Get users (YOUR OLD LOGIC)
//	    NotificationDesignationVO headerVO = config.getNotificationDesignationVO();
//
//	    List<String> nameList =
//	            Arrays.asList(headerVO.getDesignationname().split(","));
//
//	    List<EmployeeMasterVO> employees =
//	            employeeMasterRepo.findByDesignationIn(nameList);
//
//	    List<String> employeeCodes = employees.stream()
//	            .map(EmployeeMasterVO::getEmployeeCode)
//	            .toList();
//
//	    List<Long> userIds =
//	            userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//	    if (userIds == null || userIds.isEmpty()) {
//
//	        return; // no users → skip
//	    }
//
//	    // 👉 5. Save notification
//	    for (Long userId : userIds) {
//
//	        NotificationVO n = new NotificationVO();
//
//	        n.setUserid(userId);
//	        n.setMessage(msg);
//	        n.setNotificationType(config.getScreenName());
//	        Object orgId = getFieldValue(newObj, "orgId");
//	        Object createdBy = getFieldValue(newObj, "createdBy");
//	        Object updatedBy = getFieldValue(newObj, "updatedBy");
//
//	        if (orgId != null) {
//	            n.setOrgId(Long.valueOf(orgId.toString()));
//	        }	
//
//	        if (createdBy != null) {
//	            n.setCreatedBy(createdBy.toString());
//	        }
//
//	        if (updatedBy != null) {
//	            n.setUpdatedBy(updatedBy.toString());
//	        }
//
//	        notificationRepo.save(n);
//	    }
//	}
//	
//	private String generateMessage(
//	        NotificationDesignationDetailsVO config,
//	        Object oldObj,
//	        Object newObj,
//	        String action) {
//
//	    // 👉 CREATE
//	    if ("CREATE".equals(action)) {
//
//	        List<String> fields = parseFields(config.getCreateFields());
//
//	        String msg = config.getCreateMessage();
//
//	        for (String field : fields) {
//
//	            Object value = getFieldValue(newObj, field);
//
//	            msg = msg.replace("{" + field + "}", String.valueOf(value));
//	        }
//
//	        return msg;
//	    }
//
//	 // 👉 UPDATE
//	    List<String> fields = parseFields(config.getUpdateFields());
//
//	    StringBuilder finalMsg = new StringBuilder();
//
//	    for (String field : fields) {
//
//	        // 🔥 SPECIAL HANDLING FOR PRICE (child list)
//	        if ("price".equalsIgnoreCase(field)) {
//
//	            Object oldListObj = getFieldValue(oldObj, "itemPriceSlabVO");
//	            Object newListObj = getFieldValue(newObj, "itemPriceSlabVO");
//
//	            if (oldListObj instanceof List && newListObj instanceof List) {
//
//	                List<?> oldList = (List<?>) oldListObj;
//	                List<?> newList = (List<?>) newListObj;
//
//	                if (oldList.size() != newList.size()) {
//	                    finalMsg.append("Price list changed").append(", ");
//	                    continue;
//	                }
//
//	                for (int i = 0; i < oldList.size(); i++) {
//
//	                    Object oldPrice = getFieldValue(oldList.get(i), "price");
//	                    Object newPrice = getFieldValue(newList.get(i), "price");
//
//	                    if (!isEqual(oldPrice, newPrice)) {
//
//	                        finalMsg.append("Price changed from ")
//	                                .append(oldPrice)
//	                                .append(" to ")
//	                                .append(newPrice)
//	                                .append(", ");
//	                    }
//	                }
//	            }
//
//	            continue; // skip normal logic
//	        }
//
//	        // 👉 NORMAL FIELD (itemName etc.)
//	        Object oldVal = getFieldValue(oldObj, field);
//	        Object newVal = getFieldValue(newObj, field);
//
//	        if (!Objects.equals(oldVal, newVal)) {
//
//	            String msg = config.getUpdateMessage()
//	                    .replace("{field}", field)
//	                    .replace("{old}", String.valueOf(oldVal))
//	                    .replace("{new}", String.valueOf(newVal));
//
//	            finalMsg.append(msg).append(", ");
//	        }
//	    }
//
//	    // ✅ fallback
//	    if (finalMsg.length() == 0) {
//
//	        List<String> fallbackFields = parseFields(config.getCreateFields());
//	        String field = fallbackFields.get(0);
//
//	        Object value = getFieldValue(newObj, field);
//
//	        return config.getScreenName() + " updated: " + String.valueOf(value);
//	    }
//
//	    return config.getScreenName() + ": " + finalMsg.toString();
//	}
//	
//	private List<String> parseFields(String fields) {
//	    return Arrays.asList(fields.replace("[", "")
//	            .replace("]", "")
//	            .replace("\"", "")
//	            .split(","));
//	}
//	
//	
//	private Object getFieldValue(Object obj, String fieldName) {
//	    try {
//	        Field field = obj.getClass().getDeclaredField(fieldName);
//	        field.setAccessible(true);
//	        return field.get(obj);
//	    } catch (Exception e) {
//	        return null;
//	    }
//	}
//	
//	
//	private boolean isEqual(Object oldVal, Object newVal) {
//
//	    if (oldVal == null && newVal == null) return true;
//	    if (oldVal == null || newVal == null) return false;
//
//	    try {
//	        java.math.BigDecimal o1 = new java.math.BigDecimal(oldVal.toString());
//	        java.math.BigDecimal o2 = new java.math.BigDecimal(newVal.toString());
//
//	        return o1.compareTo(o2) == 0; // 🔥 correct numeric comparison
//	    } catch (Exception e) {
//	        return oldVal.equals(newVal);
//	    }
//	}
//}
	
	
	 @Override
	    public void generateNotification(
	            String screenCode,
	            Long id,
	            Object oldObj,
	            Object newObj) {

	        NotificationDesignationDetailsVO config =
	                notificationDesignationDetailsRepo.findByScreenCode(screenCode);

	        if (config == null) return;

	        String action = (oldObj == null) ? "CREATE" : "UPDATE";

	        String msg = generateMessage(config, oldObj, newObj, action);

	        NotificationDesignationVO headerVO = config.getNotificationDesignationVO();

	        List<String> designationList =
	                Arrays.asList(headerVO.getDesignationname().split(","));

	        List<EmployeeMasterVO> employees =
	                employeeMasterRepo.findByDesignationIn(designationList);

	        List<String> employeeCodes = employees.stream()
	        	    .map(EmployeeMasterVO::getEmployeeCode)
	        	    .filter(code -> code != null && !code.trim().isEmpty())
	        	    .toList();

	        	System.out.println("Employee Codes: " + employeeCodes);
	        	
	        	for (EmployeeMasterVO e : employees) {
	        	    System.out.println("Emp: " + e.getEmployeeName() +
	        	        " | Code: " + e.getEmployeeCode());
	        	}

	        List<Long> userIds =
	                userRepo.findUserIdsByEmployeeCodes(employeeCodes);

	        if (userIds == null || userIds.isEmpty()) return;

	        for (Long userId : userIds) {

	            NotificationVO n = new NotificationVO();

	            n.setUserid(userId);
	            n.setMessage(msg);
	            n.setNotificationType(config.getScreenName());

	            Object orgId = getFieldValue(newObj, "orgId");
	            Object createdBy = getFieldValue(newObj, "createdBy");
	            Object updatedBy = getFieldValue(newObj, "updatedBy");

	            if (orgId != null) n.setOrgId(Long.valueOf(orgId.toString()));
	            if (createdBy != null) n.setCreatedBy(createdBy.toString());
	            if (updatedBy != null) n.setUpdatedBy(updatedBy.toString());

	            notificationRepo.save(n);
	        }
	    }

	    // ================= MESSAGE GENERATOR =================
	    private String generateMessage(
	            NotificationDesignationDetailsVO config,
	            Object oldObj,
	            Object newObj,
	            String action) {

	        // ---------- CREATE ----------
	    	if ("CREATE".equals(action)) {

	    	    List<String> fields = parseFields(config.getCreateFields());
	    	    String msg = config.getCreateMessage();

	    	    // 🔥 Build dynamic value string
	    	    List<String> values = new ArrayList<>();

	    	    for (String field : fields) {

	    	        Object value = getFieldValue(newObj, field);
	    	        String valStr = convertValue(value);

	    	        if (valStr != null && !valStr.isEmpty()) {
	    	            values.add(field + ": " + valStr);
	    	        }
	    	    }

	    	    String finalValue = String.join(", ", values);

	    	    msg = msg.replace("{createfields}", finalValue);

	    	    // 🔥 Replace {createfields}
	    	    msg = msg.replace("{createfields}", finalValue);

	    	    return msg;
	    	}

	        // ---------- UPDATE ----------
	        List<String> fields = parseFields(config.getUpdateFields());
	        StringBuilder finalMsg = new StringBuilder();

	        for (String field : fields) {

	            Object oldVal = getFieldValue(oldObj, field);
	            Object newVal = getFieldValue(newObj, field);

	            String oldStr = convertValue(oldVal);
	            String newStr = convertValue(newVal);

	            if (!isEqualValue(oldVal, newVal)) {

	                String msg = config.getUpdateMessage()
	                        .replace("{field}", field)
	                        .replace("{old}", oldStr)
	                        .replace("{new}", newStr);

	                finalMsg.append(msg).append(", ");
	            }
	        }

	        // fallback
	        if (finalMsg.length() == 0) {

	            List<String> fallbackFields = parseFields(config.getCreateFields());
	            String field = fallbackFields.get(0);

	            Object value = getFieldValue(newObj, field);

	            return config.getScreenName() + " updated: " + convertValue(value);
	        }

	        String result = finalMsg.toString().trim();

	        if (result.endsWith(",")) {
	            result = result.substring(0, result.length() - 1).trim();
	        }

	        return config.getScreenName() + ": " + result;
	    }

	    // ================= FIELD RESOLVER (HEADER + CHILD) =================
	    private Object getFieldValue(Object obj, String fieldName) {

	        if (obj == null || fieldName == null) return null;

	        try {
	            // 🔹 HEADER FIELD
	            Field field = obj.getClass().getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return field.get(obj);

	        } catch (NoSuchFieldException e) {

	            // 🔹 CHILD LIST FIELD
	            try {
	                for (Field f : obj.getClass().getDeclaredFields()) {

	                    f.setAccessible(true);
	                    Object value = f.get(obj);

	                    if (value instanceof List<?>) {

	                        List<?> list = (List<?>) value;

	                        if (!list.isEmpty()) {

	                            Object first = list.get(0);

	                            try {
	                                Field childField =
	                                        first.getClass().getDeclaredField(fieldName);

	                                childField.setAccessible(true);

	                                List<Object> values = new ArrayList<>();

	                                for (Object item : list) {
	                                    Object v = childField.get(item);
	                                    if (v != null) values.add(v);
	                                }

	                                return values;
	                            } catch (NoSuchFieldException ignore) {
	                            }
	                        }
	                    }
	                }

	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }

	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    // ================= VALUE FORMATTER =================
	    private String convertValue(Object val) {

	        if (val == null) return "";

	        if (val instanceof List<?>) {
	            return ((List<?>) val).stream()
	                    .filter(Objects::nonNull)
	                    .map(this::convertValue)
	                    .distinct()
	                    .collect(Collectors.joining(", "));
	        }

	        try {
	            java.math.BigDecimal bd =
	                new java.math.BigDecimal(val.toString());

	            return bd.stripTrailingZeros().toPlainString();

	        } catch (Exception e) {
	            return String.valueOf(val);
	        }
	    }

	    // ================= FIELD PARSER =================
	    private List<String> parseFields(String fields) {
	        return Arrays.asList(fields.replace("[", "")
	                .replace("]", "")
	                .replace("\"", "")
	                .split(","));
	    }
	    
	    private boolean isEqualValue(Object oldVal, Object newVal) {

	        if (oldVal == null && newVal == null) return true;
	        if (oldVal == null || newVal == null) return false;

	        try {
	            java.math.BigDecimal bd1 =
	                    new java.math.BigDecimal(oldVal.toString().replace(",", "").trim());

	            java.math.BigDecimal bd2 =
	                    new java.math.BigDecimal(newVal.toString().replace(",", "").trim());

	            return bd1.compareTo(bd2) == 0;

	        } catch (Exception e) {
	            return convertValue(oldVal)
	                    .equals(convertValue(newVal));
	        }
	    }
	   
	    
	}