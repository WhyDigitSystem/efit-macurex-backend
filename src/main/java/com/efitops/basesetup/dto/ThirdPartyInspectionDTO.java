package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyInspectionDTO {

	private Long id;
	private LocalDate docDate;
	private String grnNo;
	private String workOrderNo;
	private String poNo;
	private String customerName;
	private String supplierName;
	private String thirdPartyDetails;
	private String thirdPartyAddress;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean cancel;
	List<ThirdPartyInspectionDetailsDTO> thirdPartyInspectionDetailsDTO;
	List<ThirdPartyAttachmentDTO> thirdPartyAttachmentDTO;
	

}
