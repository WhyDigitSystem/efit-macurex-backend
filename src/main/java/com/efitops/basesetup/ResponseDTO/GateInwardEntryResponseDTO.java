	package com.efitops.basesetup.ResponseDTO;
	
	import java.time.LocalDate;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class GateInwardEntryResponseDTO {
		  private Long id;
		  private BranchResponseDTO branch;
		  private CustomerResponseDetailsDTO customer;
		  private String address;
		  private String docType;
		  private String modvatCopyReceived;
		  private String supplierInvoiceNumber;
		  private LocalDate supplierInvoiceDate;
		  private String invoiceNumber;
		  private String timeOfEntry;
		  private String active;
		  private Long orgId;
		  private String createdBy;
		  private String cancelRemarks;
	
	}
