package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobWorkOutDTO {
	
	private Long id;
	private String jobWorkOrderNo;
	private LocalDate jobWorkOrderDate;
	private String dcNo;
	private String routeCardNo;
	private String quotationNo;
	private String poNo;
	private String contractorName;
	
	private String taxType; 
	
	private String destination;
	
	private String contractorCode;
	
	private String dispatchedThrough;
	
	private String durationOfProcess;
	
	
	private String termsOfPayment;
	
	
	private Long orgId;

	private String branch;
	private String branchCode;
	private String finYear;
	
	private String createdBy;
	private boolean active;
	private String narration;
	private String scIssueNo;
	
	List<JobWorkOutDetailsDTO> jobWorkOutDetailsDTO;

	

}
