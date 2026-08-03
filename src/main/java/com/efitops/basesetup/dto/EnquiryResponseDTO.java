package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.ResponseDTO.CustomerResonse1DTO;
import com.efitops.basesetup.ResponseDTO.EnquiryCusContactResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryResponseDTO {

	 private Long id;
	 private String enquiryNo;
	 private BranchResponseDTO branch;
	 private CustomerResonse1DTO customerVO;
	 private EnquiryCusContactResponseDTO contactName;
	 private String enquiryType;
	 private LocalDate enquiryDate;
	 private String partyName;
	 private String partyRefNo;
	 private LocalDate partyRefDate;
	 private LocalDate enquiryDueDate;
	 private String contactEmail;
	 private String status;
	 private Long orgId;
	 private String createdBy;
	 private String cancelRemarks;
//	 private String description;
	 private String active;
	 
	 private List<EnquiryDetailsReponseDTO> enquiryDetails;

	 private List<EnquiryTermsandCondResponseDTO> enquiryTermsandCond;

	 private List<EnquiryAttachmentResponseDTO> enquiryAttachmentDTO;
}