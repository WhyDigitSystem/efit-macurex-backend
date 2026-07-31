package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeMasterResponseDTO {
	private Long id;

	private String gradeCode;
	private String gradeDescription;

}
