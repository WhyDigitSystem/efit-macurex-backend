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
@Table(name = "initial_sample_inspection_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InitialSampleInspectionVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_sample_inspection_basicgen")
	@SequenceGenerator(name = "initial_sample_inspection_basicgen", sequenceName = "initial_sample_inspection_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_sample_inspection_basic_id")
	private Long id;
	
	  @ManyToOne
	  @JoinColumn(name = "branch")
	  private BranchVO branch;
	  
	  @Column(name = "doc_id")
	  private String docId;

	  @Column(name = "doc_date")
	  private LocalDate docDate = LocalDate.now();
	  
      @ManyToOne
	  @JoinColumn(name = "department")
	  private DepartmentVO department;
      
      @ManyToOne
      @JoinColumn(name = "supplier_id")
      private CustomerVO supplierId;

      @Column(name = "supplier_name")
      private String supplierName;
      
      @Column(name = "issue_no")
      private String issueNo;

      @Column(name = "issue_date")
      private LocalDate issueDate;
      
      @ManyToOne
      @JoinColumn(name = "item_code")
      private ItemMasterVO itemCode;

      @Column(name = "item_description")
      private String itemDescription;

      @Column(name = "drawing_no")
      private String drawingNo;
      
      @Column(name = "no_of_samples")
      private Integer noOfSamples;

      @Column(name = "batch_no")
      private String batchNo;

      @Column(name = "sample_weight")
      private BigDecimal sampleWeight;
      
   // =========================
   // SAMPLE SUMMARY
   // =========================

   @Column(name = "accepted_qty")
   private BigDecimal acceptedQty;

   @Column(name = "deviation_on_accepted_qty")
   private BigDecimal deviationOnAcceptedQty;

   @Column(name = "accepted_qty_segregation")
   private BigDecimal acceptedQtySegregation;

   @Column(name = "rework_qty")
   private BigDecimal reworkQty;

   @Column(name = "total_accepted_qty")
   private BigDecimal totalAcceptedQty;

   @Column(name = "rejected_qty")
   private BigDecimal rejectedQty;

   @Column(name = "decision")
   private String decision;

   @Column(name = "reason_for_final_inspection")
   private String reasonForFinalInspection;

   @Column(name = "comment")
   private String comment;

   @ManyToOne
   @JoinColumn(name = "prepared_by")
   private EmployeeMasterVO preparedBy;

   @Column(name = "prepared_date")
   private LocalDate preparedDate;
   
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
	private String screenName = "INITIALSAMPLEINSPECTION";
	@Column(name = "screen_code")
	private String screenCode = "ISAI";
	
	
	@OneToMany(mappedBy = "initialSampleInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InitialSampleInspectionDetailVO> initialSampleInspectionDetailVO = new ArrayList<>();
	
	
	
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
