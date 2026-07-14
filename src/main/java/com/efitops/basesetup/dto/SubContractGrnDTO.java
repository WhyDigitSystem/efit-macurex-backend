package com.efitops.basesetup.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractGrnDTO {
	private Long id;
	private String dcNo;
	private String subContractorName; 
	private String subContractorCode; 
	private String subContractorAddress;
	private String routeCardNo;
	private String scIssueNo;
	private String poNo;
	private String gstNo;
	private String gstType;
	private String currency;
	private String remarks;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String jobWorkOutOrderDocId;
	private String finYear;
	private String createdBy;
	private String updatedBy;
	private String cancelRemarks;
	
	private List<SubContractGrnDetailsDTO>subContractGrnDetailsDTO;
}
