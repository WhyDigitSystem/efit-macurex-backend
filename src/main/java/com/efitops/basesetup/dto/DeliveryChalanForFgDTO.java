package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChalanForFgDTO {
	private Long id;
	private String customerName;
	private String customerAddress;
	private String soNo;
	private LocalDate soDate;
	private LocalDate duDate;
	private String vehicleType;
	private String vehicleNo;

	// Summary

	private String naration;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;

	List<DeliveryChallanForFgDetailsDTO> deliveryChallanForFgDetailsDTO;

}
