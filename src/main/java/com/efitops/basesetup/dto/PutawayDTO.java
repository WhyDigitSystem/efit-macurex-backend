package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutawayDTO {
	
	private Long id;
	private String grnNo;
	private LocalDate grnDate;
	private String supplier;
	private String vehicleNo;
	private String fromLocation;
	private String toLocation;
	private String goodsType;
	private String dcNo;
	private String narration;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private String status;
	
	List<PutawayDetailsDTO> putawayDetailsDTO;
}
