package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEntryDTO {

	private Long id;
	private String customerName;
	private String customerCode;
	private String woNo;
//	private LocalDate woDate;
	private String fgPartName;
	private String fgPartDesc;
	private int fgQty;
	private int batchQty;
	private String rmType;
	private String rmSize;
	private String rmbatchNo;
	private int rmQty;
	private String narration;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private String status;

	@Column(name = "invoice")
	private String invoice;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "qty")
	private int qty;
	private int stockQty;

//    private MultipartFile file;

	List<RouteCardClosureDTO> routeCardClosureDTO;
	List<RouteCardEngDeptDTO> routeCardEngDeptDTO;
	List<RouteCardEntryDetailsDTO> routeCardEntryDetailsDTO;

}
