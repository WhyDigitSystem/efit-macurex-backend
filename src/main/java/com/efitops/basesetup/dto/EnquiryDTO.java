package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDTO {
	private Long id;
	private String enquiryType;
	private String customer;
	private String customerCode;
	private LocalDate enquiryDueDate;
	private String contactName;
	private Long contactNo;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private boolean active;
	private String createdBy;
	List<EnquiryDetailsDTO> enquiryDetailsDTO;
	List<EnquirySummaryDTO> enquirySummaryDTO;
}
