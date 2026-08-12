package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import com.efitops.basesetup.entity.GSTRateMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesReturnDetailsDto {
	
	 private Long id;

	    private Long item;

	    private String itemDescription;

	    private String hsnSacCode;

	    private String taxType;

	    private Long  taxPercentage;
	    
	    private Long unit;

	    private BigDecimal stock;

	    private BigDecimal qtySold;

	    private BigDecimal receivedQty;

	    private BigDecimal rate;

	    private BigDecimal rateInSelectedCurrency;

	    private BigDecimal amountInSelectedCurrency;

	    private BigDecimal amount;

	    private BigDecimal sgstRate;

	    private BigDecimal sgstAmount;

	    private BigDecimal cgstRate;

	    private BigDecimal cgstAmount;

	    private BigDecimal igstRate;

	    private BigDecimal igstAmount;
	    
	    

	}


