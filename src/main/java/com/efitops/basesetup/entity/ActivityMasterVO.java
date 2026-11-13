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
@Table(name = "activity_master_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMasterVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_master_basicgen")
	@SequenceGenerator(name = "activity_master_basicgen", sequenceName = "activity_master_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "activity_master_basic_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "department")
	 private DepartmentVO department;

	 @Column(name = "activity")
	 private String activity;
	 
	    @Column(name = "active")
		private boolean active;
	    
	    @Column(name = "org_id")
		private Long orgId;
	    
	    @Column(name = "financial_year")
	    private String financialYear;

		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "ACTIVITYMASTER";
		@Column(name = "screen_code")
		private String screenCode = "AM";
		
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
