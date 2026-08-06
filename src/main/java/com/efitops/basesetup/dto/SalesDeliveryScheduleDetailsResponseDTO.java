package com.efitops.basesetup.dto;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDetailsResponseDTO {

    private Long id;

    // S.O No
    private Long salesContractId;
    private String salesContractNo;

    // Invoice Type
    private Long salesContractDetailsId;
    private String invoiceType;

    // Item
    private Long itemId;
    private String itemCode;
    private String itemDescription;

    // Unit
    private String unit;

    // Quantities
    private Double orderQty;
    private Double pendingQty;
    private Double actualPlannedQty;

}