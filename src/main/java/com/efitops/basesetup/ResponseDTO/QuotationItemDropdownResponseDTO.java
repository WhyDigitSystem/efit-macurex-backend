package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItemDropdownResponseDTO {

    private Long itemId;
    private String itemCode;
    private String itemDescription;
    private String hsnCode;
    private String customerPartNo;
    private BigDecimal rate;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal igst;
    private Long unitMasterId;
    private String unitId;
    private Long gstRateMasterId;


}
