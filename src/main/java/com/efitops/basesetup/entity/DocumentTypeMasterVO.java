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
@Table(name = "documenttypemaster")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocumentTypeMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypemastergen")
	@SequenceGenerator(name = "documenttypemastergen", sequenceName = "documenttypemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypemaster_id")
    private Long id;
	
	 @Column(name = "code")
	 private String code;
     @Column(name = "name")
	 private String name;
     @Column(name = "description")
	 private String description;
     @Column(name = "doc_code")
	 private String docCode;
     @Column(name = "financial_year")
	 private String financialYear;
     
     
     
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
 	private String screenName="documenttypemaster";
 	@Column(name = "screen_code")
 	private String screenCode="DTM";
 	
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

     


