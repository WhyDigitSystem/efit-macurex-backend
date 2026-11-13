package com.efitops.basesetup.ResponseDTO;

	
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueDetailsResponseDTO {

    private Long id;
    private String itemDescription;
    private BigDecimal availableQty;
    private BigDecimal indReqQty;
    private BigDecimal indPendQty;
    private BigDecimal issueQty;
    private BigDecimal rate;
    private BigDecimal amount;

    // Master Dropdowns
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
}