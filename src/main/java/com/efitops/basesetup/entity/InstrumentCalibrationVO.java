package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrument_calibration_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instrument_calibration_basicgen")
	@SequenceGenerator(name = "instrument_calibration_basicgen", sequenceName = "instrument_calibration_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "instrument_calibration_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@ManyToOne
	@JoinColumn(name = "checked_by")
	private EmployeeMasterVO checkedBy;
	
	@Column(name = "selectMachineInstNo")
	private String selectMachineInstNo;
	
	@ManyToOne
	@JoinColumn(name = "machine_inst_no")
	private MachineMasterVO machineInstNo;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;
	
	@ManyToOne
	@JoinColumn(name = "calibration_agency")
	private ListOfValuesDetailsVO calibrationAgency;
	
	@Column(name = "certificate_no")
	private String certificateNo;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;
	
	
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "screen_code")
	private String screenCode = "IC";

	@Column(name = "screen_name")
	private String screenName = "INSTRUMENT CALIBRATION";
	
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
	
	@OneToMany(mappedBy = "instrumentCalibrationVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<InstrumentCalibrationDetailsVO> instrumentCalibrationDetailsVO = new ArrayList<>();

}
