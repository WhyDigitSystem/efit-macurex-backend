package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParameterDTO {

	private Long id;
	private Long orgId;
	private Long userId;
	private Long branchId;
	private String financialYear;
}
