package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentDTO {
	private Long id;
	
	private Long branch;
	
	private String belongTo;

	private String docId;

	private LocalDate docDate;

	private Long department;

	private String timeOfIndent;

	private String approvedByPM;

	private Long preparedBy;

	private Long authorizedBy;

	private String remarks;

	private Long orgId;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private String financialYear; 	
	
	private List<InternalIndentDetailsDTO>internalIndentDetailsDTO;

}
