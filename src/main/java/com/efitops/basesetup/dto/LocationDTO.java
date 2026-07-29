package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
	private Long id;
	private Long orgId;
	private Long branch;
	private Long locationType;
	private Long belongsTo;
	private String locationId;
	private String locationName;

	private String address;
	private Long phoneNo;
	private Long faxNo;
	private String email;
	private String considerMrp;
	private String createdBy;
	private String cancelRemarks;
	
	

}
