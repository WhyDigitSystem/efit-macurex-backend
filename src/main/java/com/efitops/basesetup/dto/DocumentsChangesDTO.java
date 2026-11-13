package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsChangesDTO {
	private String sopNo;

	private String stationNo;

	private LocalDate completionDate;

	private String remarks;

}
