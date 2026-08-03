package com.efitops.basesetup.ResponseDTO;

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
}
