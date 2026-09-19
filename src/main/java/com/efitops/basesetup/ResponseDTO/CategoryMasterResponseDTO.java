package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class CategoryMasterResponseDTO {

	    private Long id;

	    private ListOfValuesDetailsResponseDTO applicableFor;

	    private String category;

	    private String active;

	    private Long orgId;

	    private String createdBy;

	    private String financialYear;

	    private String cancelRemarks;
	

}
