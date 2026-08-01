package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LocationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationgen")
	@SequenceGenerator(name = "locationgen", sequenceName = "locationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "plant_id")
	private BranchVO plantId;

	@ManyToOne
	@JoinColumn(name = "location_type")
	private ListOfValuesDetailsVO locationType;

	@ManyToOne
	@JoinColumn(name = "belongs_to")
	private ListOfValuesDetailsVO belongsTo;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "location_id")
	private String locationId;

	@ManyToOne
	@JoinColumn(name = "contact_person_name")
	private EmployeeMasterVO contactPersonName;

	@ManyToOne
	@JoinColumn(name = "party_name")
	private CustomerVO partyName;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_no")
	private Long phoneNo;

	@Column(name = "fax_no")
	private String faxNo;

	@Column(name = "email")
	private String email;

	@Column(name = "consider_mrp")
	private String considerMrp;

	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by", length = 25)
	private String createdBy;
	@Column(name = "modify_by", length = 25)
	private String updatedBy;
	@Column(name = "cancel_remarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "screen_code", length = 30)
	private String screenCode = "LM";
	@Column(name = "screen_name", length = 30)
	private String screenName = "locationMaster";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
