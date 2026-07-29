package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	 private Long id;

	    private Long customerCategory;
	    private Long customerCategory1;
	    private Long customerCategory2;
	    private Long supplierType;

	    private Long branch;

	    private String docId;
	    private LocalDate docDate;

	    private String salutation;
	    private String customerType;
	    private String accountName;
	    private String customerName;
	    private String customerLegalName;
	    private String tradeName;

	    private boolean groupCompany;

	    private String zone;
	    private String vendorCode;
	    private String groupName;

	    private boolean registered;
	    private boolean excisable;

	    private BigDecimal partyCreditLimit;
	    private int partyCreditPeriod;

	    private String gstType;
	    private String gstNo;

	    private Long gstState;

	    private boolean gstApplicable;

	    private String address;

	    private Long city;
	    private Long state;
	    private Long country;

	    private String pincode;
	    private String email;
	    private String webAddress;
	    private String cinNo;

	    private BigDecimal overDueInterest;

	    private String introducedBy;
	    private String cstNo;
	    private String eccNo;
	    private String eccType;
	    private String kstNo;
	    private String phone;
	    private String contactPerson;

	    private LocalDate effectiveFrom;

	    private String range;
	    private String remarks;

	    private LocalDate dateOfApproval;

	    private String isoStatus;
	    private String typeExtentOfControl;

	    private LocalDate reAssessmentDate;

	    private int creditPeriod;

	    private boolean approved;

	    private String scopeOfSupply;
	    private String basisOfApproval;

	    private String bankName;
	    private String bankAccountNo;
	    private String paymentMode;
	    private String ifscCode;
		private String belongsTo;

		
	    private Long orgId;

	    private String createdBy;

	    private String cancelRemarks;

	    private boolean active;

	    private String financialYear;
	    
	    private List<CustomerContactDetailsDTO> customerContactDetails;
	    
	    private List<CustomerShippingDetailsDTO> customerShippingDetails;
}
