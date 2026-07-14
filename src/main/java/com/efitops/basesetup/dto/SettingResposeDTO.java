package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingResposeDTO {
	
	private String slno;
	private String characteristics;
	private String specification;
	private  String methodOfInspection;
	private String lsl;
	private String usl;
	private String setter1;
	private String quality1;
	private String remarks;

}
