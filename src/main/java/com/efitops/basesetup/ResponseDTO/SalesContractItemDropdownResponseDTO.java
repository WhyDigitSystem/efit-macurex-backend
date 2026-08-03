package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesContractItemDropdownResponseDTO {

    private Long itemId;

    private String itemCode;

    private String itemDescription;

    private String unitId;

    private BigDecimal minimumSellPrice;

    private String hsnCode;

    private String customerPartNo;

}