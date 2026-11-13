package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentDetailsDTO {

    private Long item;
    
    private Long unit;

    private Long oldQty;
    
    private Long newQty;

    private Long oldRate;
    
    private Long newRate;

   
    private LocalDate oldDeliveryDate;
    
    private LocalDate newDeliveryDate;

}