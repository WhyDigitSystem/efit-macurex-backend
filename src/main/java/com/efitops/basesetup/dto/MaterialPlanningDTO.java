package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanningDTO {

    private Long id;

    private LocalDate fromDate;

    private LocalDate docDate;

    private String mrpType;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;
    
    private Long branch;


    private String financialYear;
}