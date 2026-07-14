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
@Table(name = "rackmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackmastergen")
	@SequenceGenerator(name = "rackmastergen", sequenceName = "rackmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rackmasterid")
	private Long id;
	
	@Column(name="racklocation")
	private String rackLocation;
	@Column(name="rackno")
	private String rackNo; 
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode",length = 30)
	private String screenCode ="RM";
	@Column(name = "screenname",length = 30)
	private String screenName="RACKMASTER";
	
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
