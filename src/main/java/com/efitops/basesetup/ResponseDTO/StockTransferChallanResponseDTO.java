package com.efitops.basesetup.ResponseDTO;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;
import com.efitops.basesetup.dto.StockTransferChallanDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanResponseDTO {
	private Long id;
	private String docID;
	private LocalDate docDate;
	private BranchResponseDTO branch;
	private ListOfValuesResponseDTO types;
	private CustomerResponse1DTO customer;
	private LocationMasterResponseDTO location;
	private LocalTime timeOfTranfer;
	private String stockPosting;
	private LocalDate date;
	private int noOfPackages;
	private int otherPackages;
	private String importLocal;
	private String active;
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
	private List<StockTransferChallanDetailsDTO>stockTransferChallanDetailsDTO;
	
}
