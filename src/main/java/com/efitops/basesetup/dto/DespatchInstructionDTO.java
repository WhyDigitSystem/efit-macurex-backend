package com.efitops.basesetup.dto;



import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionDTO {
	private Long id;
	private Long branch;
//	private String docId;
//	private LocalDate docDate;
	private Long customer;
	private String schduleNo;
	private String invoiceType;
	private String schduleDate;
	private Long location;
	private String paymentTerms;
	private String modeOfTransport;
	private double netWeight;
	private double grossWeight;
	private String deliveryInstructions;
	private String Consignee;
	private Long orgId;
	private boolean active;
	private String createdBy;
	private String cancelRemarks;
	private List<DespatchInstructionDetailsDTO> despatchInstructionDetailsDTO;

}
