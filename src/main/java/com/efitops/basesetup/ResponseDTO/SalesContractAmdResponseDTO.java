package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAmdResponseDTO {
	private Long id;
	private String contractAmdNo;
	private LocalDate date;
	private BranchResponseDTO branch;
	private String contractNo;
	private String contractDate;
	private String partyPoAmdNo;
	private String partyPoAmdDate;
	private String custPoNo;
	private String custPoDate;
	private String revisionNo;
	private String remarks;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	
	private List<SalesContractDetailResponseDTO> salesContractDetailResponseDTO;

}
