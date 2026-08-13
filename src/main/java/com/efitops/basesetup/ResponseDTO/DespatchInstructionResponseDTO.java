package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class DespatchInstructionResponseDTO {
	private Long id;
	private BranchResponseDTO branch;
	private String docId;
	private LocalDate docDate;
	private CustomerResponse1DTO customer;
	private String schduleNo;
	private String invoiceType;
	private String schduleDate;
	private LocationMasterResponseDTO location;
	private Long orgId;
	private Boolean active;
	private String createdBy;
	private String cancelRemarks;
	private String paymentTerms;
	private String modeOfTransport;
	private int netWeight;
	private int grossWeight;
	private String deliveryInstructions;
	private String Consignee;
	private List<DespatchInstDetailsResponseDTO> despatchInstDetailsResponseDTO;
	
}
