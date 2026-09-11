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
@Table(name = "root_cause_analysis_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootCauseAnalysisVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "root_cause_analysis_basicgen")
	@SequenceGenerator(name = "root_cause_analysis_basicgen", sequenceName = "root_cause_analysis_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "root_cause_analysis_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
    private String docId;	
	 @Column(name = "doc_date")
	 private LocalDate docDate;
	 
	 @Column(name = "complaint_no")
	 private Long complaintNo;
	 
	 @ManyToOne
	 @JoinColumn(name = "item_code")
	 private ItemMasterVO itemCode;
	 
	 @Column(name = "complaint_date")
	 private LocalDate complaintDate;
	 
	 @Column(name = "item_description")
	 private String itemDescription;
	 
	 @Column(name = "complaint_type")
	 private String complaintType;
	 
	 @ManyToOne
	 @JoinColumn(name = "customer_id")
	 private CustomerVO customerId;
	 
	 @Column(name = "customer_name")
	 private String customerName;
	 
	 @Column(name = "customer_part_no")
	 private String customerPartNo;
	 
	 @Column(name = "details_of_complaint")
	 private String detailsOfComplaint;
	 
	 @Column(name = "active")
	 private boolean active;
	 
	 
	 //summary
	 
	 @Column(name = "narration")
	 private String narration;
	 
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
		private String screenName = "ROOTCAUSEANALYSIS";
		@Column(name = "screen_code")
		private String screenCode = "RCA";
		
		
		@OneToMany(mappedBy = "rootCauseAnalysisVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<RootCauseAnalysisDetailsVO> rootCauseAnalysisDetailsVO = new ArrayList<>();
		
		

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
