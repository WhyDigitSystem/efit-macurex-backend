package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNumberChangeDetailsDTO {
	private String documentFormateNo;
	private Long issueNo;
	private Long revisionNo;
	private LocalDate date;

}
