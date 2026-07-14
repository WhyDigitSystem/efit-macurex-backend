package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractEnquiryDTO {
	private Long id;
	private String enquiryType;
	private String subContractorName;
	private String subContractorRefNo;
	private LocalDate subContractorRefDate;
	private LocalDate enquiryDueDate;
	private String routeCardNo;
	private String contactName;
	private String contactNo;
	private String scIssueNo;
	private String narration;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;
	private List<SubContractEnquiryDetailsDTO> subContractEnquiryDetailsDTO;
}
