package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partymaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyMasterVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partymastergen")
	@SequenceGenerator(name = "partymastergen", sequenceName = "partymasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partymasterid")
	private Long id;
	    
    @Column(name = "partytype")
    private String partyType;

    @Column(name = "partycode")
    private String partyCode;

    @Column(name = "partyname")
    private String partyName;

    @Column(name = "gstpartyname")
    private String gstPartyName;

    @Column(name = "customertype")
    private String customerType;

    @Column(name = "company")
    private String company;

    @Column(name = "agentname")
    private String agentName;

    @Column(name = "accounttype")
    private String accountType;

    @Column(name = "bussinesstype")
    private String bussinessType;

    @Column(name = "carriercode")
    private String carrierCode;

    @Column(name = "suppliertype")
    private String supplierType;

    @Column(name = "salesperson")
    private String salesPerson;

    @Column(name = "customercoord")
    private String customerCoord;

    @Column(name = "accountname")
    private String accountName;

    @Column(name = "gstregistered")
    private String gstRegistered;

    @Column(name = "gstin")
    private String gstIn;

    @Column(name = "creditlimit", precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "creditdays")
    private Long creditDays;

    @Column(name = "panno")
    private String panNo;

    @Column(name = "controllingoff")
    private String controllingOff;

    @Column(name = "currency")
    private String currency;

    @Column(name = "panname")
    private String panName;

    @Column(name = "airwaybillno")
    private String airwayBillNo;

    @Column(name = "airlinecode")
    private String airLineCode;

    @Column(name = "tanno")
    private String tanNo;

    @Column(name = "bussinesscate")
    private String bussinessCate;

    @Column(name = "country")
    private String country;

    @Column(name = "caf")
    private String caf;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "compoundscheme")
    private String compoundScheme;

    @Column(name = "psugovorg")
    private String psuGovOrg;

    @Column(name = "nameofbank")
    private String nameOfBank;

    @Column(name = "addressbank")
    private String addressBank;

    @Column(name = "accountno")
    private String accountNo;

    @Column(name = "acctype")
    private String accType;

    @Column(name = "ifsccode")
    private String IfscCode;

    @Column(name = "swift")
    private String Swift;
    
    
 

    // Additional fields with column mappings
    @Column(name = "branch")
    private String branch;

    @Column(name = "branchcode")
    private String branchCode;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "modifyby")
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel;

    @Column(name = "cancelremarks")
    private String cancelRemarks;

    @Column(name = "finyear")
    private String finYear;

    @Column(name = "screencode")
    private String screenCode = "PM";

    @Column(name = "screenname")
    private String screenName = "PARTYMASTER";

    @Column(name = "orgid")
    private Long orgId;
	
    @Column(name = "creditterms")
    private String creditTerms;
    @Column(name = "partyshortname")
    private String partyShortName;
    
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyStateVO> partyStateVO;
	

	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyAddressVO> partyAddressVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyDetailsOfDirectorsVO> partyDetailsOfDirectorsVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartySpecialTDSVO> partySpecialTDSVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyChargesExemptionVO> partyChargesExemptionVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyCurrencyMappingVO> partyCurrencyMappingVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartySalesPersonTaggingVO> partySalesPersonTaggingVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyTdsExemptedVO> partyTdsExemptedVO;
	
	@OneToMany(mappedBy = "partyMasterVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PartyPartnerTaggingVO> partyPartnerTaggingVO;
	
	@OneToOne(mappedBy = "partyMasterVO",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private PartyVendorEvaluationVO partyVendorEvaluationVO;

	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
