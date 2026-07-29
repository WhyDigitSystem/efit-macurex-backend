package com.efitops.basesetup.dto;

import java.util.List;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.FinancialYearVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DocumentTypeMappingDTO {
	
	 private Long id;
	 private Long branch;
	 private Long financialYear;
	 private Long orgId;
	 private String createdBy;
	 private String cancelRemarks;
	 private String description;
	 private boolean active;
	 
	 private List<DocumentTypeMappingDetailsDTO> details;

}
