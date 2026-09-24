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
public class ProductionSchOrderShortCloseResponseDTO {
    private Long id;
    private String docId;
    private LocalDate docDate;
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

    // Nested Objects for Dropdowns
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private BranchResponseDTO branch;

    // Child Grid
    private List<ProductionOrderDetailsResponseDTO> productionOrderDetails;
}