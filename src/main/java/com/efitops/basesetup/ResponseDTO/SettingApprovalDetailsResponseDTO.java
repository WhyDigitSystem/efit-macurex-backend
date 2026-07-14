package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingApprovalDetailsResponseDTO {

	private String characteristics;
	private String specification;
	private String methodOfInspection;
	private BigDecimal lsl;
	private BigDecimal usl;
	private String setter1;
	private String setter2;
	private String setter3;
	private String setter4;
	private String setter5;
	private String quality1;
	private String quality2;
	private String quality3;
	private String quality4;
	private String quality5;
	private String remarks;
}
