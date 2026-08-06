package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanResponseDTO {
	private Long id;
	private String docID;
	private LocalDate transferDate;
	private BranchResponseDTO branch;
	private ListOfValuesResponseDTO listOfValues;
	private CustomerResonse1DTO customer;
	private LocationResponseDTO location;
	private String stockPosting;
	private LocalDate date;
	private int noOfPackages;
	private int otherPackages;
	private String importLocal;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;


}
