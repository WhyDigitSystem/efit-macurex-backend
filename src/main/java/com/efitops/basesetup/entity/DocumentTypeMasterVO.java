package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_type_master")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocumentTypeMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_type_mastergen")
	@SequenceGenerator(name = "document_type_mastergen", sequenceName = "document_type_masterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "document_type_master_id")
    private Long id;
      
 	
 	@Column(name = "screen_code",length =10)
	private String screenCode;
	
	@Column(name = "screen_name",length =150)
	private String screenName;
	
	@Column(name = "description",length =150)
	private String description;
	
	@Column(name = "doc_code",length =25)
	private String docCode;
	
	@Column(name = "created_by",length =25)
	private String createdBy;
	
	@Column(name = "modified_by",length =25)
	private String updatedBy;
	
	@Column(name="org_id")
	private Long orgId;
	
//    @Column(name = "financial_year")
//	 private String financialYear;
    
 	@Column(name = "active")
 	private boolean active;
 	 	
 	@Column(name = "cancel")
 	private boolean cancel=false;
// 	@Column(name = "cancel_remarks")
// 	private String cancelRemarks;
 	
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

     


