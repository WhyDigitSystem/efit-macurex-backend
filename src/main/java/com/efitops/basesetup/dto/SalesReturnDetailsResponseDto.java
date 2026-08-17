package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import com.efitops.basesetup.ResponseDTO.GSTRateMasterResponseDTO;
import com.efitops.basesetup.entity.GSTRateMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesReturnDetailsResponseDto {
	
	 private Long id;

	    // Item
	    private ItemMasterResponseDetailsDTO item;

	  
	    // Tax Type
	    private String taxType;
	    
	    private BigDecimal taxPercentage;
	    
	    // Unit
	    private UnitMasterResponseDTO unit;

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


