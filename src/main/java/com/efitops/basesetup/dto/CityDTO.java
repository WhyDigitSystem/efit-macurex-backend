package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
	private Long id;

	private String cityCode;
	private Long country;
	private String cityName;
	private Long state;
	private boolean active;
	private String createdBy;
	private Long orgId;
	private String cancelRemarks;

}



