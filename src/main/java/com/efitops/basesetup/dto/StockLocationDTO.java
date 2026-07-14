package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLocationDTO {

	private Long id;

	private String locationCode;

	private String locationName;

	private String company;

	private String branch;

	private LocalDate startDate;

	private LocalDate closedDate;

	private Long orgId;

	private boolean active;

	private String createdBy;

}




