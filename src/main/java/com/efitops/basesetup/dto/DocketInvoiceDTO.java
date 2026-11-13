package com.efitops.basesetup.dto;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceDTO {
	 private Long id;
//	 private String docNo;
	 private Long branch;
	 private Long transport;
//     private LocalDate docDate;
     private String billNo;
     private LocalDate billDate;
 	 private int totalAmount;
 	 private Long orgId;
 	 private boolean active;
 	 private String createdBy;
 	 private String cancelRemarks;
 	 private String  financialYear;
 	 private List<DocketInvoiceDetailsDTO> docketInvoiceDetailsDTO;
		
	 }
 	 



