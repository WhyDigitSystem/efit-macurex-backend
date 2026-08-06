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

    private Integer slNo;

    private String itemCode;

    private String itemDescription;

    private Double oldQty;

    private Double oldRate;

    private Double newQty;

    private Double newRate;

    private LocalDate oldDeliveryDate;

    private LocalDate newDeliveryDate;

}