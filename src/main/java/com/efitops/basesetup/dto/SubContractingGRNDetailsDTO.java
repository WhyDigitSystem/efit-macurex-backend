package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNDetailsDTO {

    private Long incomingItem;

    private BigDecimal stock;

    private BigDecimal tolerance;

    private Long primaryUnit;

    private String jobOrderNo;

    private BigDecimal jobOrderQty;

    private BigDecimal jobOrderRate;

    private BigDecimal gatePassQty;

    private String inspectionable;


//    private BigDecimal excessQty;

    private BigDecimal receivedQty;
    
    private BigDecimal qtyInPrimaryUnit;
    private Long location;

    private BigDecimal acceptedQty;

    private BigDecimal accQtyInPrimaryUnit;

    private BigDecimal rejectedQty;

    private BigDecimal rejQtyInPrimaryUnit;

//    private BigDecimal amount;

    private BigDecimal sgstRate;

//    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

//    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

//    private BigDecimal igstAmount;
    
    private List<SubContractingGRNConsumptionDTO> consumption;

}