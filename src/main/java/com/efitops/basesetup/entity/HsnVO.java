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
@Table(name = "hsn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HsnVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsngen")
	@SequenceGenerator(name = "hsngen", sequenceName = "hsnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "hsn_id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private ListOfValuesDetailsVO category;
	
	@Column(name = "hsn")
	private String hsn;
	@Column(name = "description")
	private String description;
	
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	@Column(name = "screen_name")
	private String screenName="CURRENCY";
	@Column(name = "screen_code")
	private String screenCode="HSN";
	
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
