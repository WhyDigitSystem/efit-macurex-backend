package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UomConversionResponseDTO {

    private Long id;

    private UnitMasterResponseDTO fromUnit;

    private UnitMasterResponseDTO toUnit;

    private Double multiplicationFactor;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String description;

    private Boolean active;

    private BranchResponseDTO branch;
}