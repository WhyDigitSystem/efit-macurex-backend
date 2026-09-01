package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

	 private Long id;

	    private PartyCategoryResponseDTO customerCategory;
	    private PartyCategoryResponseDTO customerCategory1;
	    private PartyCategoryResponseDTO customerCategory2;
	    private PartyCategoryResponseDTO supplierType;

	    private DocumentTypeMappingBranchResponseDTO branch;

	    
	    
		private String panNo;
		private String esiNo;
		private String tinNo;
		private String customerCompanyCode;

	  
		private PartyCategoryResponseDTO belongsTo;
		private EmployeeResponseDTO buyerName;

	    private String salutation;
	    private String customerType;
	    private String accountName;
	    private String customerName;
	    private String customerLegalName;
	    private String tradeName;

	    private boolean groupCompany;

	    private SalesZoneResponseDTO zone;
	    private String CustomerCode;
	    private String groupName;

	    private boolean registered;
	    private boolean excisable;

	    private BigDecimal partyCreditLimit;
	    private int partyCreditPeriod;

	    private String gstType;
	    private String gstNo;

	    private GSTStateResponseDTO gstState;

	    private boolean gstApplicable;
		private CurrencyResponseDTO primaryCurrency;

	    private String address;

	    private CityResponseDTO city;
	    private StateResponseDTO state;
	    private CountryResponseDTO country;

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

	    private Long orgId;

	    private String createdBy;

	    private String cancelRemarks;

	    private String active;

	    private String financialYear;	
	    
	    private List<CustomerContactDetailsResponseDTO> customerContactDetails;
	    
	    private List<CustomerShippingDetailsResponseDTO> customerShippingDetails;
	    
	    private List<CustomerItemDetailsResponseDTO> customerItemDetails;

}
