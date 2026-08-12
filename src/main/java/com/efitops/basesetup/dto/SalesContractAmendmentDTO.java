package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAmendmentDTO {
	private Long id;
//	private String docId;
//	private LocalDate docDate;
	private String partyPoAmdNo;
	private String partyPoAmdDate;
	private Long branch;
	private String contractNo;
	private String contractDate;
	private String custPoNo;
	private String custPoDate;
	private String revisionNo;
	private String remarks;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private String cancelRemarks;

	private List<SalesContractAmdDetailsDTO> salesContractAmdDetailsDTO;

}
