package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.efitops.basesetup.entity.GateInwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateInwardEntryDTO {

	private Long id;
	private String supplierName;
	private String supplierCode;
	private String poNumber;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String vehicleNo;
	private String courierNo;
	private String courierName;
	private String narration;
	private String createdBy;
	private Long orgId;
	private String branch;
	private String branchCode;
    private String finYear;
	
	List<GateInwardEntryDetailsDTO> gateInwardEntryDetailsDTO;
}
