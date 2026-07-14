package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEngDeptDTO {
	private String preparedBy;
	private LocalDate preparedDate;
	private String approvedBy;
	private LocalDate approvedDate;

}
