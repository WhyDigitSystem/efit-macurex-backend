package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grademaster")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GradeMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grademastergen")
	@SequenceGenerator(name = "grademastergen", sequenceName = "grademasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grademaster_id")
    private Long id;
	
	 @Column(name = "grade_code")
	 private String gradeCode;
     @Column(name = "grade_description")
	 private String gradeDescription;
     @Column(name = "remarks")
     private String remarks;
     
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
 	@Column(name = "branch")
 	private String branch;
 	@Column(name = "branch_code")
 	private String branchCode;
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
  }


