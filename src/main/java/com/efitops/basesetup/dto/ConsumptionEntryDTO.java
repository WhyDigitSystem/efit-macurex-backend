package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionEntryDTO {

	private Long id;
	private String type;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String consumption;
	private Long location;
	private Long entryType;
	private String narration;

	private String createdBy;
	private Long orgId;
	private String financialYear;
	private Long branch;
	private boolean active;
	private String cancelRemarks;

	private List<ConsumptionEntryDetailsDTO> consumptionEntryDetailsDTO;
	private List<RmConsumptionEntryDetailsDTO> rmConsumptionEntryDetailsDTO;
}