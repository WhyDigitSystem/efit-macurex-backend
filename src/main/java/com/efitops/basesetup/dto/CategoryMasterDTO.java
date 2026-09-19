package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMasterDTO {
	  private Long id;

	    private Long applicableFor;

	    private String category;

	    private boolean active;

	    private Long orgId;

	    private String createdBy;

	    private String financialYear;

	    private String cancelRemarks;

	  

}
