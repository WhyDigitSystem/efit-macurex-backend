package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private LocalDate schDates;
    private String schOrderNo;
    private BomFgResponseDTO bom;
    private String alterInputItem;
    private String itemType;
    private BigDecimal issueQty;
    private String unit;
    private BigDecimal value;
    private BigDecimal rate;
    private String sfgDescription;
    private BigDecimal totalValue;
    private String remarks;
    
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private String screenName;
    private String screenCode;


    private LocationMasterResponseDTO fromLocation;
    private LocationMasterResponseDTO toLocation;
    private LocationMasterResponseDTO scrapToLocation;
    private ItemMasterDetailsResponseImportDTO fgPartNo;
    private ItemMasterDetailsResponseImportDTO sfgPartNo;
    private BranchResponseDTO branch;

    // Details List
    private List<ProductionTransferSlipDetailsResponseDTO> productionTransferSlipDetailsResponseDTO;
}