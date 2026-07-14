package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyInspectionDetailsDTO {

	private String itemId;
	private String itemDesc;
	private String inspectionType;
	private String certificateNo;
	private String status;
	private String remarks;

}
