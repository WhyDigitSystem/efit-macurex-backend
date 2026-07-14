package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobWorkOutDetailsDTO {
	
	private String part;
	
	private String partDesc;
	
	private String process;
	
	private String taxCode;

	private BigDecimal quantityNos;

	private Date dueOn;
	
	private String inspectionable;
	
	private BigDecimal rate; 
	
	
	private BigDecimal discount;
	
	
	
	private BigDecimal sgst;
	
	private BigDecimal cgst;
	
	private BigDecimal igst;
	
	

}
