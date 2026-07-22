
package com.efitops.basesetup.dto;

import java.util.List;

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
	private Long countryId;
	private String currency;
	private String mainCurrency;
	private String address;
	private String zip;
	private Long cityId;
	private Long stateId;
	private String phone;
	private String email;
	private String webSite;
	private String note;
	private String cin;
	//private String userId;
	private String employeeName;
	private String employeeCode;
	private String password;
	private String createdBy;
	private String updatedBy;
	private boolean cancel;
	private boolean active;
	private String ceo;
	private String gst;
	private int role;
	private String termsAndConditions;
	private String panNo;
	
//	private byte[] companyLogo;
	
	List<BankDetailsDTO>bankDetailsDTO;

}
