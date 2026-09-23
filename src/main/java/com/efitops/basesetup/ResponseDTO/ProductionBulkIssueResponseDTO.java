package com.efitops.basesetup.ResponseDTO;


import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueResponseDTO {

    private Long id;
    private String docId;
    private String belongsTo;
    private LocalDate docDate;
    private String fgItemDescription;
    private String indentNo;
    private LocalDate issueDate;
    private String purchaseMaterialRef;
    private String type;
    private String refNo;
    private String remarks;
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;


    private ItemMasterDetailsResponseImportDTO fgItem;
    private LocationResponseDTO fromLocation;
    private LocationResponseDTO toLocation;
    private BranchResponseDTO branch;

    private List<ProductionBulkIssueDetailsResponseDTO> itemDetails;
}