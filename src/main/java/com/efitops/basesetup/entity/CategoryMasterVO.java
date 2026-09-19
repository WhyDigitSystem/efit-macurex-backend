package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

@Table(name = "category_master_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_master_basicgen")
	@SequenceGenerator(name = "category_master_basicgen", sequenceName = "category_master_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "category_master_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "applicable_for")
	private ListOfValuesDetailsVO applicableFor;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "financial_year")
	private String FinancialYear;
	
	
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "CATEGORY MASTER";
	@Column(name = "screen_code")
	private String screenCode = "CM";

	@JsonGetter("activeStatus")
	public String getActiveStatus() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
