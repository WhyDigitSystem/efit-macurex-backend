package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceDetailsDTO {

//	private Long id;

	private Long item;

	private BigDecimal taxPercentage;

	private BigDecimal despatchQty;	

	private BigDecimal orderRate;

	private String hsnCode;
	
	private String taxType;

}
