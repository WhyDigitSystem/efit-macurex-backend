package com.efitops.basesetup.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatrolInspectionDocumentsDTO {

	@Column(name = "documentname")
	private String documentName;

	@Column(name = "documenttype")
	private String documentType;

}
