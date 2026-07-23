
package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

	private Long id;

	private String companyCode;
	private String companyName;
	private String email;
	private String phoneNo;
	private String panNo;
	private String gst;
	private String cin;
	private String officialWebsite;
	private String industryType;
	private String companySize;

	private Long countryId;

	private Long stateId;

	private Long cityId;

	private String pincode;

	private String ceo;
	private String registeredAddress;

	private String selectPlan;
	private int trialPeriod;
	private String maxUsers;
	private String storageLimit;

	private String adminName;
	private String adminEmail;
	private String adminMobileNo;

	private String password;
	private String conformPassword;

//	private byte[] companyLogo;

	private String createdBy;
	private String updatedBy;

	private boolean active;
	private String cancelRemarks;

	private String termsAndConditions;

//	List<BankDetailsDTO>bankDetailsDTO;

}
