package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ZeroEntryDetailDTO {
	
	
	  private Long id;

	    private String partNo;

	    private String partName;

	    private Long failureQty;

	    private String reason;

}
