package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class SalesZoneMasterDTO {
	
    private Long id;
    private String zoneId;
    private String zonedescription;
    private Long orgId;
    private String createdBy;
	private String cancelRemarks;
	private String description;
    private boolean active;
		
		


}
