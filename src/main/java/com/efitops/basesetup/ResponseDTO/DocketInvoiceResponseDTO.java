package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceResponseDTO {
	 private Long id;
	 private String docId;
	 private BranchResponseDTO branch;
	 private TransportResponseDTO transport;
     private LocalDate docDate;
     private String billNo;
     private LocalDate billDate;
	 private int totalAmount;
	 private Long orgId;
	 private String active;
	 private String createdBy;
	 private String cancelRemarks;
	 private String financialYear;
	 private List<DocketInvoiceDetResponseDTO>docketInvoiceDetResponseDTO;

}
