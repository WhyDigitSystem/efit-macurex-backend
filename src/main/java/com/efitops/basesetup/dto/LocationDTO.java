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
	private String locationId;
	private String locationName;
	private String address;
	private Long phoneNo;
	private String faxNo;
	private String email;
	private String considerMrp;
	private String createdBy;
	private String cancelRemarks;

	private Long branchId;

	private Long plantId;

	private Long locationTypeId;

	private Long belongsToId;

	private Long contactPersonNameId;

	private Long partyNameId;

	private String financialYear;

}
