package com.efitops.basesetup.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecieveFromSubcontractDTO {

	private Long id;
	private String routeCardNo;
	private String issueNo;
	private Date issueDate;
	private String jobWorkOutOrder;
	private String dcNo;
	private String department;
	private String contractorName;
	private String contractorId;
	private String invoiceNo;
	private String testCertificate;

	private Long orgId;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	private String branch;
	private String branchCode;
	private String finYear;

	List<RecieveFromSubContractDetailsDTO> recieveFromSubContractDetailsDTO;

}
