package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QADRegisterDTO {

	private Long id;
	private String docname;
	private String docformatno;
	private String approvedby;
	private String narration;
	private String createdBy;
	private String cancelRemarks;
	private String branch;
	private String branchCode;
	private String finYear;
	private Long orgId;
	private String summary;
	private List<QADRegisterDetailsDTO> qADRegisterDetailsDTO;
}
