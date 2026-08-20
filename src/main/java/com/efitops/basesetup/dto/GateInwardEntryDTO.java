package com.efitops.basesetup.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateInwardEntryDTO {
	  private Long id;
	  private Long branch;
	  private Long customer;
	  private String address;
	  private String docType;
	  private String modvatCopyReceived;
	  private String supplierInvoiceNumber;
	  private LocalDate supplierInvoiceDate;
	  private String invoiceNumber;
	  private String timeOfEntry;
	  private boolean active;
	  private Long orgId;
	  private String createdBy;
	  private String cancelRemarks;

}
