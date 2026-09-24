package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
@Table(name = "machine_tools_scrap_note_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolsScrapNoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_tools_scrap_note_basicgen")
	@SequenceGenerator(name = "machine_tools_scrap_note_basicgen", sequenceName = "machine_tools_scrap_note_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machine_tools_scrap_note_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "belongs_to")
	private ListOfValuesDetailsVO belongsTo;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO departement;
	
	@Column(name = "time")
	private LocalTime time;
	
	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;
	
	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;
	
	@ManyToOne
	@JoinColumn(name = "authorized_by")
	private EmployeeMasterVO authorizedBy;
	
	@Column(name = "production_approval")
	private String productionApproval;
	
	@Column(name = "quality_approval")
	private String qualityApproval;
	
	@Column(name = "store_approval")
	private String storeApproval;
	
	@Column(name = "narration")
	private String narration;
	
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
	private String screenCode = "MTSN";

	@Column(name = "screen_name")
	private String screenName = "MACHINE TOOLS SCRAP NOTE";
	
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
	
	@OneToMany(mappedBy = "machineToolsScrapNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineToolsScrapNoteDetailsVO> machineToolsScrapNoteDetailsVO = new ArrayList<>();
	
	@OneToMany(mappedBy = "machineToolsScrapNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineToolScrapNoteAttachmentVO> machineToolScrapNoteAttachmentVO = new ArrayList<>();
	

}
