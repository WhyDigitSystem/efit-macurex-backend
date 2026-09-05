package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentDTO {
	
	 private Long id;
	 
	 private Long branch;
	 
	 private String belongsTo;
	 
	 private Long customer;
	 
	 private String purchaseordernumber;
	 
	 private Long currency;
	 
//	 private String refNo;
//	 
//	 private String refDate;
	 
	 private Long exchangeRate;
	 
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
	 
	 private List<PurchaseOrderAmendmentDetailsDTO> details;
	 
	

	 

}
