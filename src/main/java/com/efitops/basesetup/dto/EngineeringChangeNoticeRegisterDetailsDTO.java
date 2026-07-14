package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineeringChangeNoticeRegisterDetailsDTO {
	private String intEcNno;
	private String customer;
	private String encRefNo;
	private String partName;
	private LocalDate oldRevDate;
	private LocalDate dateRev;
	private String detailsOfRevision;
	private String reasonForRevision;
	private String oldRev;
	private String verified;
	private String slNo;
    private String remarks;

}
