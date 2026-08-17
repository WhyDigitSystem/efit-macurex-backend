package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanDTO {
	private Long id;
	private Long branch;
	private Long types;
	private Long customer;
	private Long location;
	private String stockPosting;
	private LocalDate date;
	private LocalTime timeOfTranfer;
	private int noOfPackages;
	private int otherPackages;
	private String importLocal;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	
	private double totalInsurance;
	private double totalFreight;
	private double totalAssVal;
	private String modeOfTransport;
	private String salesTax;
	private double grossAmount;
	private String amountInWords;
	private String deliverTo;
	private String paymentTerms;
	private String narration;
	private List<StockTransferChallanDetailsDTO>stockTransferChallanDetailsDTO;
	private List<StockTransferChallanTaxDetailsDTO>stockTransferChallanTaxDetailsDTO;
	
	


}
