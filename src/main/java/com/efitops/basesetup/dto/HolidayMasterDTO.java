package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayMasterDTO {

	private Long id;
	private Long branch;
	private LocalDate date;
	private String createdBy;
	private Boolean active;
	private String cancelRemarks;
	private Long orgId;
	
	private List<HolidayMasterDetailsDTO> details;

	
	
}
