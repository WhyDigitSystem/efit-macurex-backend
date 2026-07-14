package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcForSubContractDTO {

	private Long id;
	private String scIssueNo;
	private String customerName;
	private String customerAddress;
	private String routeCardNo;
	private String gstNo;
	private String subContractorName;
	private String subContractoraddress;
	private String subContractorId;
	private String vehicleNo;
	private LocalDate Duedate;
	private String dispatchThrough;
	private String ewayBillNo;
	private String narration;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String branch;
	private String branchCode;
	private String finYear;
	
	List<DcForSubContractDetailsDTO> dcForSubContractDetailsDTO;

}
