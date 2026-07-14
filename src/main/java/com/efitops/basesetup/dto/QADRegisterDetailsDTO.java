package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QADRegisterDetailsDTO {
	private LocalDate date;
	private String documentNo;
	private long olddocissue;
	private String olddocrev;
	private long newdocissue;
	private String newdocrev;
	private String admendmentdetails;
	private String reasonforadmendment;
	private String remarks;
	private String reviewedby;

}
