package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderImportDetailsResponseDTO {

    private Long id;

    private String indentNo;

    private String indentDate;

    private ItemMasterDetailsResponseDTO item;

    private UnitResponseDTO uom;

    private BigDecimal indentQty;

    private BigDecimal poQty;

    private BigDecimal fobRateFc;

    private BigDecimal fobValueFc;

    private BigDecimal fobRateInr;

    private BigDecimal fobValueInr;

}