package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPatrolInspectionDetailsResponseDTO {

	private Long id;

	private String characteristic;
	private String methodOfInspection;
	private String lsl;
	private String usl;
	private String sample1;
	private String sample2;
	private String sample3;
	private String sample4;
	private String sample5;
	private String sample6;
	private String sample7;
	private String sample8;
	private String sample9;
	private String sample10;
	private String status;
	private String remarks;

}
