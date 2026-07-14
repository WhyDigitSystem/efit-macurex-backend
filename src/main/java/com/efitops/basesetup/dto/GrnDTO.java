package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDTO {
	
	private Long id;
	private String location;
	private String InwardNo;
	private String supplierName;
	private String supplierCode;
	private String poNo;
	private String gstNo;
	private String gstType; 
	private String address;
	private String currency;
	private BigDecimal exchangeRate;
	private String grnClearTime;
	private String invDcNo;
	private Date invDcDate;
	private String customer; 
	private String status;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	List<GrnDetailsDTO> grnDetailsDTO;

}
