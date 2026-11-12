package com.efitops.basesetup.ResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionEntryResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private LocalTime shiftTimeFrom;
    private LocalTime shiftTimeTo;
    private String shift;
    private BigDecimal productionQty;
    private String schOrderNo;
    private String processSheetNo;
    private BigDecimal totalLabourCost;
    private BigDecimal totalMachineCost;
    private BigDecimal totalToolCost;
    private BigDecimal totalConsumablesCost;
    private String narration;
    
    // Audit
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;

    private ItemMasterDetailsResponseImportDTO fgItemCode;
    private LocationMasterResponseDTO location;
    private EmployeeMasterResponseDetailsDTO preparedBy;
    private EmployeeMasterResponseDetailsDTO approvedBy;
    private BillOfMaterialDropdownResponseDTO bomId;
    private BranchResponseDTO branch;

    // Details Lists
    private List<ProductionEntryDetailsResponseDTO> productionEntryDetailsResponseDTO;
    private List<ToolDetailsResponseDTO> toolDetailsResponseDTO;
    private List<StoppageReasonResponseDTO> stoppageReasonResponseDTO;
    private List<ReworkReasonResponseDTO> reworkReasonResponseDTO;
    private List<ScrapDetailsResponseDTO> scrapDetailsResponseDTO;
}
