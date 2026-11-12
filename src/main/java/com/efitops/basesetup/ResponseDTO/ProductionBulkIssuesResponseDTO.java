package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssuesResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private String belongsTo;

    private LocalDate date;

    private ItemResponse1DTO fgItem;

    private String type;

    private String indentNo;

    private String purchaseMaterialRef;

    private String refNo;

    private LocationMasterResponseDTO fromLocation;

    private LocationMasterResponseDTO toLocation;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<ProductionBulkIssuesDetailsResponseDTO> details;
}