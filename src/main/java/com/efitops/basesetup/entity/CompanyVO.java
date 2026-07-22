package com.efitops.basesetup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.efitops.basesetup.entity.BankDetailsVO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companygen")
	@SequenceGenerator(name = "companygen", sequenceName = "companyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "company_id")
	private Long id;

	@Column(name = "company_code")
	private String companyCode;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone_no")
	private String phoneNo;
	@Column(name = "pan_no", length = 10)
	@Size(min = 10, max = 10, message = "PanNo must be exactly 10 characters.")
	private String panNo;
	@Column(name = "gst")
	private String gst;
	@Column(name = "cin")
	private String cin;
	@Column(name = "official_website")
	private String officialWebsite;
	@Column(name = "industry_type")
	private String industryType;
	@Column(name = "company_size")
	private String companySize;
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryVO country;
	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityVO city;
	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateVO state;
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "ceo")
	private String ceo;
	@Column(name = "registered_address")
	private String RegisteredAddress;

	@Column(name = "select_plan")
	private String selectPlan;

	@Column(name = "trial_period")
	private int trialPeriod;
	
	@Column(name = "max_users")
	private String maxUsers;
	
	@Column(name = "storage_limit")
	private String storageLimit;
	
	@Column(name = "admin_name")
	private String adminName;
	@Column(name = "admin_email")
	private String adminEmail;
	@Column(name = "admin_mobileno")
	private String adminMobileNo;

	@Column(name = "password")
	private String password;
	@Column(name = "conform_password")
	private String conformPassword;
	
	@Lob
	@Column(name = "company_logo", columnDefinition = "LONGBLOB") // Ensure the column is LONGBLOB
	private byte[] companyLogo;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "terms_conditions", length = 10000)
	private String termsAndConditions;
	@Column(name = "active")
	private boolean active;


    @Column(name = "screencode", length = 5)
    private String screenCode = "CMP";

    @Column(name = "screenname", length = 25)
    private String screenName = "COMPANY";
    
//	@OneToMany(mappedBy = "companyVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	List<BankDetailsVO> bankDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}