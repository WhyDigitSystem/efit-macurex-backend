package com.efitops.basesetup.dto;

import java.time.LocalDate;


import com.efitops.basesetup.ResponseDTO.CustomerResonse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerComplaintResponseDTO {
	
	private Long id;
	private Long qtyNo;
	private String image;
	private String remarks;
	private BranchResponseDTO branch;
//	private CustomerResonse1DTO customerName;
	private String buyerName;
	private DepartmentResponseDTO department;
	private ItemResponse1DTO item;
	private String detailsOfComplaint;
	private String preparedBy;
	private String userCategory;
	private String financialYear;
	private String prefix;
	private String complaintNo;
	private LocalDate complaintDate;
	private String complaintType;
	private String customerRefNo;
	private CustomerResonse1DTO customer;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private String updatedby;

}
