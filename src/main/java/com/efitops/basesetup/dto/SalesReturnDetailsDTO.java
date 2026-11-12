package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesReturnDetailsDTO {
	

	    private Long item;

	    private Long hsnSacCode;

	    private String taxType;

	    private String  taxPercentage;
	    
	    private Long unit;

	    private BigDecimal stock;
	    
	    private BigDecimal qtySold;

	    private BigDecimal receivedQty;

	    private BigDecimal rate;

	    private BigDecimal rateInSelectedCurrency;

	    private BigDecimal amountInSelectedCurrency;


	    private BigDecimal sgstRate;

	    private BigDecimal cgstRate;

	    private BigDecimal igstRate;
	    

	}


