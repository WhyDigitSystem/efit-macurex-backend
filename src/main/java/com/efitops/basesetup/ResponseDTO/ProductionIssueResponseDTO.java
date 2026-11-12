package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
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
public class ProductionIssueResponseDTO {
    private Long id;
    private String docId;
    private String belongsTo;
    private LocalDate docDate;
    private String indentNo;
    private LocalDate issueDate;
    private String schOrderNo;
    private String type;
    private BigDecimal totalValue;
    private String narration;
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
    private LocationMasterResponseDTO fromLocation;
    private LocationMasterResponseDTO toLocation;
    private BranchResponseDTO branch;

  
    private List<ProductionIssueDetailsResponseDTO> itemDetails;
}