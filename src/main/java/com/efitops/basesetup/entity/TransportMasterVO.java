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
import javax.validation.Valid;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transport")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class TransportMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transportgen")
	@SequenceGenerator(name = "transportgen", sequenceName = "transportseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "transport_id")
	private Long id;
	
	@Column(name = "transport_name")
	private String transportName;
	
	@Column(name = "address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updated_By;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	
	
	
   
	@Column(name = "screen_code",length = 10)
	private String screenCode ="TM";
	@Column(name = "screen_name",length = 30)
	private String screenName="Transport Master";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

	
	
	
	
	
	
}
