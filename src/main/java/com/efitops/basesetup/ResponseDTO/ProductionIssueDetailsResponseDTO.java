package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionIssueDetailsResponseDTO {
    private Long id;
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private BigDecimal availableQty;
    private String grnNo;
    private LocalDate grnDate;
    private BigDecimal intReqQty;
    private BigDecimal intPendQty;
    private BigDecimal issueQty;
    private BigDecimal itemMinQty;
    private BigDecimal rate;
    private BigDecimal amount;
}