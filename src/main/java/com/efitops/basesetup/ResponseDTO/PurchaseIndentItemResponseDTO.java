package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentItemResponseDTO {

    private Long id;

    private String itemCode;

    private String itemDescription;
    
    private String primaryUnit;
    
    private String purchaseUnit;
    

}