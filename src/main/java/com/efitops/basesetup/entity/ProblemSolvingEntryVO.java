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
@Table(name = "problem_solving_entry_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingEntryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "problem_solving_entry_basicgen")
	@SequenceGenerator(name = "problem_solving_entry_basicgen", sequenceName = "problem_solving_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "problem_solving_entry_basic_id")
	private Long id;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@Column(name = "reference")
	private String reference;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "machine_no")
	private String machineNo;
	
	@Column(name = "mfg_date")
	private LocalDate mfgDate;
	
	@Column(name = "defect_description")
	private String defectDesciption;
	
	@ManyToOne
	@JoinColumn(name = "team_member1")
	private EmployeeMasterVO teamMember1;
	
	@ManyToOne
	@JoinColumn(name = "team_member2")
	private EmployeeMasterVO teamMember2;
	
	@Column(name = "short_term_action")
	private String shortTeamAction;
	
	@Column(name = "close_date")
	private LocalDate closeDate;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;
	
	@Column(name = "recognizw_the_team")
	private String recognizeTheTeam;
	
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
	private String screenCode = "PSE";

	@Column(name = "screen_name")
	private String screenName = "Problem Solving Entry";

    @OneToMany(mappedBy = "problemSolvingEntryVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProblemSolvingRootDetailsVO> problemSolvingRootDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "problemSolvingEntryVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProblemSolvingOtherDetailsVO> problemSolvingOtherDetailsVO = new ArrayList<>();
    
    @OneToMany(mappedBy = "problemSolvingEntryVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProblemSolvingActionDetailsVO> problemSolvingActionDetailsVO = new ArrayList<>();


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
