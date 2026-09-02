package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "control_plan_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_plan_basicgen")
	@SequenceGenerator(name = "control_plan_basicgen", sequenceName = "control_plan_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "control_plan_basic_id")
	private Long id;
	
	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	 @Column(name = "revision_date")
	 private LocalDate revisionDate;

	  @ManyToOne
	  @JoinColumn(name = "control_plan_type")
	  private ListOfValuesDetailsVO controlPlanType;
	  
	  @Column(name = "plan_no")
	  private String planNo;
	  
	  @ManyToOne
	  @JoinColumn(name = "fg_item_code")
	  private ItemMasterVO fgItemCode;
	  
	   @Column(name = "item_description")
	   private String itemDescription;
	   
	   @ManyToOne
	   @JoinColumn(name = "item_grade")
	   private GradeMasterVO itemGrade;
	   
	   @Column(name = "item_size")
	   private String itemSize;
	   
	   @Column(name = "process_sheet_no")
	   private String processSheetNo;
	   
	   @ManyToOne
	   @JoinColumn(name = "prepared_by")
	   private EmployeeMasterVO preparedBy;
	   
	   @ManyToOne
	   @JoinColumn(name = "checked_by")
	   private EmployeeMasterVO checkedBy;
	   
	   @Column(name = "approved")
	   private boolean approved;
	   
	   @Column(name = "active")
	   private boolean active;
	   
	   @Column(name = "org_id")
		private Long orgId;

		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "CONTROLPLAN";
		@Column(name = "screen_code")
		private String screenCode = "CP";
		
		@OneToMany(mappedBy = "controlPlanVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<ControlPlanDetailVO> controlPlanDetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "controlPlanVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<ControlPlanParameterVO> controlPlanParameterVO = new ArrayList<>();
		
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
