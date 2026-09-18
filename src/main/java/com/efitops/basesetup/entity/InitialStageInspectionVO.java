package com.efitops.basesetup.entity;

import java.time.LocalDate;
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
@Table(name = "initial_stage_inspection_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class InitialStageInspectionVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_stage_inspection_basicgen")
	@SequenceGenerator(name = "initial_stage_inspection_basicgen", sequenceName = "initial_stage_inspection_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_stage_inspection_basic_id")
	private Long id;
	
	    @ManyToOne
	    @JoinColumn(name = "branch")
	    private BranchVO branch;
	    
	    @Column(name = "doc_id")
	    private String docId;
	  
	    @Column(name = "doc_date")
	    private LocalDate docDate = LocalDate.now();

	    @Column(name = "shift")
	    private String shift;

	    @ManyToOne
	    @JoinColumn(name = "item_code")
	    private ItemMasterVO itemCode;

	    @Column(name = "item_description")
	    private String itemDescription;

	    @Column(name = "party_drawing_no")
	    private String partyDrawingNo;

	    @Column(name = "drawing_no")
	    private String drawingNo;

	    @ManyToOne
	    @JoinColumn(name = "prepared_by")
	    private EmployeeMasterVO preparedBy;

	    @Column(name = "prepared_date")
	    private LocalDate preparedDate;

	    @ManyToOne
	    @JoinColumn(name = "grade_type")
	    private GradeMasterVO gradeType;

	    @ManyToOne
	    @JoinColumn(name = "party_id")
	    private CustomerVO partyId;

	    @Column(name = "party_name")
	    private String partyName;
	    
	    @Column(name = "work_order_no")
	    private String workOrderNo;
	    
	    @Column(name = "process_sheet_no")
	    private String processSheetNo;
	    
	 // Summary

	    @Column(name = "reason_for_initial_inspection")
	    private String reasonForInitialInspection;

	    @Column(name = "comment")
	    private String comment;

	    @Column(name = "recommended_for_production")
	    private String recommendedForProduction;
	    
	    
		
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
		private String screenName = "INITIALSTAGEINSPECTION";
		@Column(name = "screen_code")
		private String screenCode = "ISI";
		
		@OneToMany(mappedBy = "initialStageInspectionVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<InitialStageInspectionDetailVO> initialStageInspectionDetailVO = new ArrayList<>();
		
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
