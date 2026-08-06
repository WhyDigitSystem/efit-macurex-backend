package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceResponseDTO {
	 private Long id;
	 private String docNo;
	 private Long branch;
	 private Long transport;
     private LocalDate docDate;
     private String billNo;
     private LocalDate billDate;
	 private int totalAmount;
	 private Long orgId;
	 private Boolean active;
	 private String createdBy;
	 private String cancelRemarks;
	 private List<DocketInvoiceDetResponseDTO>docketInvoiceDetResponseDTO;

}
