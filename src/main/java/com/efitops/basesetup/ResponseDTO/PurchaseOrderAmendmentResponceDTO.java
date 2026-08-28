package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderAmendmentResponceDTO {
	
	 private Long id;
	 
	 private BranchResponseDTO branch;
	 
	 private String belongsTo;
	 
	 private String docId;
	 private LocalDate docDate;
	 
	 private CustomerResponseDetailsDTO customer;
	 
     private String purchaseordernumber;
	 
	 private CurrencyResponseDTO  currency;
	 
//	 private String refNo;
//	 
//	 private String refDate;
	 
	 private PurchaseOrderAmendmentExcahngeRateResponseDTO exchangeRate;
	 
	 private int revisionNo;
	 
	 private boolean active;
	 
	 
	 private String freightType;
	 
	 private String packingType;
	 
	 private BigDecimal insuranceAmount;
	 
	 private String modeOfDespatch;
	 
	 private String taxDescription;
	 
	 private String remarks;
	 
	 
	 private Long orgId;

	 private String createdBy;
	 
	 private String cancelRemarks;
	 
	 private String screenName;
	 private String screenCode;
	 
	 private List<PurchaseOrderAmendmentDetailsResponseDTO> details;

	 private List<PurchaseOrderAmendmentAttachmentResponseDTO> attachments;

}
