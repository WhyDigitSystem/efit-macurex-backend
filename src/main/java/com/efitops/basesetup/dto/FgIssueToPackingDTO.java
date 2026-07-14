package com.efitops.basesetup.dto;

import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FgIssueToPackingDTO {

	private Long id;	
	private String fromDept;
	private String toDept;
	private String routeCardNo;
	private String createdBy;
	private String approvedBy;
	private String remarks;
	private String narration;
	private Long orgId;
	
	List<FgIssueToPackingDetailsDTO> fgIssueToPackingDetailsDTO;
}
