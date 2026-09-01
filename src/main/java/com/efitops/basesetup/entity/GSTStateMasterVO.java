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
@Table(name = "gststatemaster")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GSTStateMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gststatemastergen")
	@SequenceGenerator(name = "gststatemastergen", sequenceName = "gststatemasterrseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gststatemaster_id")
    private Long id;
	
	@Column(name = "state_code")
    private String stateCode;
    @Column(name = "state_name")
    private String stateName;
    @Column(name = "gst_state_id")
    private String gstStateId;
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

	@Column(name = "screen_name")
	private String screenName="GST STATE MASTER";
	@Column(name = "screen_code")
	private String screenCode="GSTSM";
	
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
