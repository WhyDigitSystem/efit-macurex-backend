package com.efitops.basesetup.dto;

import java.util.List;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDetailsDTO {

//	private Long id;

	private String soNoContractNo;

	private String invoiceType;

	private Long item;
    private Long unit;

    private double orderQty;
    private double pendingQty;
	private double actualPlannedQty;

	private List<SalesDeliverySchedulePlanDTO> deliverySchedules;

}