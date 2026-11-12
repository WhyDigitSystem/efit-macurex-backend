package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillDetailDTO {
	
	
	 private Long id;

	    private String scGrnNo;

	    private Long incomingItemCode;

	    private String incomingItemDescription;

	    private Long unit;

	    private BigDecimal challanQty;

	    private BigDecimal receivedQty;

	    private BigDecimal shortageQty;

	    private BigDecimal grnAcceptedQty;

	    private BigDecimal rejectedQty;

	    private BigDecimal rate;

	    private BigDecimal amount;

	    private BigDecimal sgstRate;

	    private BigDecimal sgstAmount;

	    private BigDecimal cgstRate;

	    private BigDecimal cgstAmount;

	    private BigDecimal igstRate;

	    private BigDecimal igstAmount;

	    private String supplierDcNo;

	    private LocalDate supplierDcDate;
	    

}
