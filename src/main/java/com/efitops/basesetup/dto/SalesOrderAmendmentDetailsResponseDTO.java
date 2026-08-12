package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAmendmentDetailsResponseDTO {

    private Long id;

    

    private String itemCode;

    private String itemDescription;

    private double oldQty;

    private double oldRate;

    private double newQty;

    private double newRate;

    private LocalDate oldDeliveryDate;

    private LocalDate newDeliveryDate;

}