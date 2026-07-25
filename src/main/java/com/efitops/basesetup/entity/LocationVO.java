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
	@JoinColumn(name = "location_type")
	private ListOfValuesVO locationType;
	
	@ManyToOne
	@JoinColumn(name = "belongs_to")
	private ListOfValuesVO belongsTo;
	
	
	@Column(name = "location_id")
	private String locationId;
	
	@ManyToOne
	@JoinColumn(name = "listofvalues_id")
	private ListOfValuesVO listOfValues;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_no")
	private Long phoneNo;
	
	@Column(name = "fax_no")
	private Long faxNo;
	
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
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel=false;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
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
