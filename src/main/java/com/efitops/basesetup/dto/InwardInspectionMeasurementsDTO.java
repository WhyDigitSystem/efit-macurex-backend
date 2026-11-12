package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionMeasurementsDTO {

    private Long id;

    private String parameters;

    private String type;

    private String spec;

    private String accCriteria;

    private String uom;

    private String test1;

    private String test2;

    private String test3;

    private String test4;

    private String test5;

    private String status;

    private String remarks;

}