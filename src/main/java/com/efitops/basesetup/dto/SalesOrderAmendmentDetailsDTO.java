package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAmendmentDetailsDTO {

    private Long id;

   
    private Long item;

   
    private double oldQty;

    private double oldRate;

   
    private double newQty;

    private double newRate;

    private LocalDate oldDeliveryDate;

    private LocalDate newDeliveryDate;
}