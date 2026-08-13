package com.efitops.basesetup.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;

import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanDTO {
	private Long id;
	private String docID;
	private LocalDate docDate;
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
	
	private Double totalInsurance;
	private Double totalFreight;
	private Double totalAssVal;
	private String modeOfTransport;
	private String salesTax;
	private Double grossAmount;
	private String amountInWords;
	private String deliverTo;
	private String paymentTerms;
	private String narration;
	
	
	


}
