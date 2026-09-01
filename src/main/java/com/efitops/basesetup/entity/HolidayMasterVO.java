package com.efitops.basesetup.entity;

import java.time.LocalDate;

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
@Table(name = "holiday")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holidaygen")
	@SequenceGenerator(name = "holidaygen", sequenceName = "holidayseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "holiday_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "holiday_date")
	private LocalDate holidayDate;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "holiday_type")
	private String holidayType;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "compensatory")
	private String compensatory;
	
	@Column(name = "compensatory_date")
	private LocalDate compensatoryDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String  updatedBy;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name="org_id")
	private Long orgId;
	@Column(name="active")
    private boolean active;
	@Column(name="cancel")
    private boolean cancel;
	@Column(name = "screen_name")
	private String screenName="HOLIDAYMASTER";
	
	@Column(name = "screen_Code")
	private String screenCode="HM";
	
	
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
