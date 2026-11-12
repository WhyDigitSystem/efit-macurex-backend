package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingOfPartyToAccDTO {
	private Long id;

	private LocalDate asOnDate;
	private Long branch;
	private Long category;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private boolean active;

	
	private List<MappingDetailsDTO> details;

 

}
