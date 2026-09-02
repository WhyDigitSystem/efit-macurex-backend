package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.MatchesPattern;
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
@Table(name = "tool_master_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_basicgen")
	@SequenceGenerator(name = "tool_master_basicgen", sequenceName = "tool_master_basicseq", initialValue = 1000000002, allocationSize = 1)
	@Column(name = "tool_master_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "type")
	private ListOfValuesDetailsVO type;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@Column(name = "tool_no")
	private String toolNo;
	
	@Column(name = "tool_description")
	private String toolDescription;
	
//dependecy from pmchecklist
	@Column(name = "pm_checklist_no")
	private String PMChecklistNo;
	
//	toolcategory from category master dependency
	@Column(name = "tool_category")
	private String toolCategory;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;
	
	@Column(name = "drawing_no")
	private String drawingNo;
	
	@Column(name = "serial_no")
	private String serialNo;
	
	@Column(name = "manufactured_by")
	private String manufacturedBy;
	
	@Column(name = "section")
	private String section;
	
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "made_in")
	private ListOfValuesDetailsVO madeIn;
	
	@ManyToOne
	@JoinColumn(name = "purchase_from")
	private CustomerVO purchaseFrom;
	
	@ManyToOne
	@JoinColumn(name = "mode_of_purchase")
	private ListOfValuesDetailsVO modeOfPurchase;
	
	@ManyToOne
	@JoinColumn(name = "tool_incharge")
	private EmployeeMasterVO toolIncharge;
	
//	dependency from operation master
	@Column(name = "tool_used_for")
	private String toolUsedFor;
	
	@ManyToOne
	@JoinColumn(name = "tool_ownership")
	private CustomerVO toolOwnership;
	
	@ManyToOne
	@JoinColumn(name = "present_location")
	private LocationVO presentLocation;
	
	@Column(name = "tool_cost")
	private BigDecimal toolCost;
	
	@Column(name = "cavity_number")
	private String cavityNumber;
	
	@Column(name = "remarks")
	private String remarks;
	
//	technical info
	@Column(name = "tool_weight")
	private BigDecimal toolWeight;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;
	
	@Column(name = "tool_fixture_size")
	private String toolFixtureSize;
	
	@Column(name = "life_of_tool")
	private String lifeOfTool;
	
	@ManyToOne
	@JoinColumn(name = "life_type")
	private ListOfValuesDetailsVO lifeType;
	
	@Column(name = "recondition_freq")
	private BigDecimal reconditionFreq;
	
	@Column(name = "set_up_time_in_minutes")
	private BigDecimal setUpTimeInMinutes;
	
	@Column(name = "completed_life_cycle")
	private BigDecimal completedLifeCycle;
	
	@Column(name = "tool_made_of")
	private String toolMadeOf;
	
	@Column(name = "technical_specification")
	private String technicalSpecification;
	
	@Column(name = "no_of_strokes_completed")
	private BigDecimal noOfStokesCompleted;
	
	@Column(name = "strokes_completed_after_reconditioning")
	private BigDecimal strokesCompletedAfterReconditioning;
	
	@Column(name = "reconditioned_date")
	private LocalDate reconditionedDate;
	
	@Column(name = "tool_fixture_cost")
	private BigDecimal toolFixtureCost;
	
	@Column(name = "tool_fixture_amortized_recovered")
	private BigDecimal toolFixtureAmortizedRecovered;
	
//	images 
	
	@Column(name = "tool_name")
	private String toolName;
	
	@Column(name = "tool_image")
	private String image;
	
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
	private String screenCode = "TM";

	@Column(name = "screen_name")
	private String screenName = "TOOL MASTER";

   
    
    @OneToMany(mappedBy = "toolMasterVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ToolMasterSpareDetailsVO> toolMasterSpareDetailsVO = new ArrayList<>();
    
    @OneToMany(mappedBy = "toolMasterVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ToolMasterComponentOutPutDetailsVO> toolMasterComponentOutPutDetailsVO = new ArrayList<>();
    
    @OneToMany(mappedBy = "toolMasterVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ToolMasterMachineHistoryDetailsVO> toolMasterMachineHistoryDetailsVO = new ArrayList<>();
    
    @OneToMany(mappedBy = "toolMasterVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ToolMasterAttachementVO> toolMasterAttachementVO = new ArrayList<>();

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
