package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NcProductRegisterDTO {

	private Long id;

	private String ncProductId;
	private LocalDate ncProductDate;
	private Long docNo;

	private String createdBy;
	private String modifiedBy;
	private String updatedBy;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;

	private List<NcProductRegisterDetailsDTO> ncProductRegisterDetailsDTO;
}
