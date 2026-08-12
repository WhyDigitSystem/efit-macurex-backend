package com.efitops.basesetup.dto;

import java.util.List;

import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDetailsResponseDTO {

    private Long id;
    private String soNocontractNo;
    
    private String invoiceType;

    private ItemResponseDTO item;

    private double orderQty;

    private double pendingQty;

    private double actualPlannedQty;

    private List<SalesDeliverySchedulePlanResponseDTO> deliverySchedules;
}