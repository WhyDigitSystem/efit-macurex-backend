package com.efitops.basesetup.dto;

import java.time.LocalDate;

import com.efitops.basesetup.entity.CustomerVO;
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
	private LocalDate transferDate;
	private Long branch;
	private Long listOfValues;
	private Long customer;
	private Long location;
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
