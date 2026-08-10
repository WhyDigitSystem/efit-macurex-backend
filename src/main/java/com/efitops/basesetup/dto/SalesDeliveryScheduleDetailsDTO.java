package com.efitops.basesetup.dto;

import java.util.List;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDetailsDTO {

    private Long id;

    private Long salesContractId;

    private Long salesContractDetailsId;

    private Long itemId;

    private Double actualPlannedQty;

    private List<SalesDeliverySchedulePlanDTO> deliverySchedules;

}