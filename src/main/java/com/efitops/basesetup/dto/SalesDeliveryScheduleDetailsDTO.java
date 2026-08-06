package com.efitops.basesetup.dto;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDetailsDTO {

    private Long id;

    private Long salesContractId;

    private Long salesContractDetailsId;

    private Long itemId;

    private Double actualPlannedQty;

}