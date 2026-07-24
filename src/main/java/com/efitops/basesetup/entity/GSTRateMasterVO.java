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

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gstratemaster")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GSTRateMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gstratemastergen")
	@SequenceGenerator(name = "gstratemastergen", sequenceName = "gstratemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gstratemaster_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "category")
	private String category;
	@Column(name = "hsn_code")
	private String hsncode;
	@Column(name = "description")
	private String description;
	
	@Column(name = "wef")
	private String wef;
	
	@Column(name = "igst_rate")
	private Double igstRate;
	
	@Column(name = "sgst_rate")
	private Double sgstRate;
	
	@Column(name = "cgst_rate")
	private Double cgstRate;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "taxable")
	private String taxable;
	
	

	
	
	
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
	
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchVO branch;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "GSTRM";
	@Column(name = "screenname", length = 30)
	private String screenName = "GSTRateMaster";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	
}



