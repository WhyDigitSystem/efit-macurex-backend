package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UomConversionDTO {
	
	  private Long id;
	  private Long fromUnit;
	  private Long toUnit;
	  private  double multiplicationFactor;
	  private Long orgId;
	  private String createdBy;
	  private String cancelRemarks;
	  private String description;
	  private boolean active;
	  private Long branch;
		

}
