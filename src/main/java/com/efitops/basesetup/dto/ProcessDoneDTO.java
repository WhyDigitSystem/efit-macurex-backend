package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.ProcessDoneDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDoneDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;
	private String customerName;
	private String routeCardNo;
	private String jobOrderNo;
	private String fgPartName;
	private String fgPartNo;
	private String from;
	private String to;
	private String placingLocation;
	private int qty;
//	Defaul Fields
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active = true;
//	Summary Fields
	private String narration;

	List<ProcessDoneDetailsDTO> processDoneDetailsDTO;
}
