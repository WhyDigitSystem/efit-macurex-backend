package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssuesDTO {

    private Long id;

    private Long branch;

    private String belongsTo;

    private LocalDate date;

    private Long fgItem;

    private String type;

    private String indentNo;

    private String purchaseMaterialRef;

    private String refNo;

    private Long fromLocation;

    private Long toLocation;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;
    

    private Long orgId;

    private String financialYear;

    private List<ProductionBulkIssuesDetailsDTO> details;
}