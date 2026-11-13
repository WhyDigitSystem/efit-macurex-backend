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
@Table(name = "initial_planning_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_planning_basicgen")
	@SequenceGenerator(name = "initial_planning_basicgen", sequenceName = "initial_planning_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_planning_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item_type")
	private ListOfValuesDetailsVO itemType;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@ManyToOne
	@JoinColumn(name = "item_grade")
	private GradeMasterVO item_grade;

	@Column(name = "drawing_no")
	private String drawingNo;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private CustomerVO source;
	
	@Column(name = "material_characteristics")
	private String materialCharacteristics;
	
	@Column(name = "sampling_plan")
	private String samplingPlan;
	
	@Column(name = "process")
	private String process;
	
	@Column(name = "aesthetics")
	private String aesthetics;
	
	@Column(name = "packing_requirements")
	private String packingRequirements;
	
	@Column(name = "others")
	private String others;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;
	
	
	@Column(name = "approved")
	private String approved;
	
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
	private String screenCode = "INIP";

	@Column(name = "screen_name")
	private String screenName = "INITIAL PLANNING";

    @OneToMany(mappedBy = "initialPlanningVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InitialPlanningDetailsVO> initialPlanningDetailsVO = new ArrayList<>();

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
