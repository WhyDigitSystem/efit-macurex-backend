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
@Table(name = "uomconversion")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UomConversionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uomconversiongen")
	@SequenceGenerator(name = "uomconversiongen", sequenceName = "uomconversionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "uomconversion_id")
    private Long id;
	
	@Column(name = "from_unit")
    private Long fromUnit;
	@Column(name = "to_unit")
    private Long toUnit;
	@Column(name = "multiplication_factor")
    private  double multiplicationFactor;
	
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
	
//	@ManyToOne
//	@JoinColumn(name = "branch_id")
//	private BranchVO branch;
	
	@Column(name = "screen_name")
	private String screenName="UNITMASTER";
	@Column(name = "screen_code")
	private String screenCode="UM";
	
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
