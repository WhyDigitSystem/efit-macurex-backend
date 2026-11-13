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
@Table(name = "eight_discipline_entry_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDisciplineEntryVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_entry_basicgen")
	@SequenceGenerator(name = "eight_discipline_entry_basicgen", sequenceName = "eight_discipline_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_entry_basic_id")
	private Long id;
	
	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	 @Column(name = "complaint_type")
	 private String complaintType;
	 
	 @ManyToOne
	 @JoinColumn(name = "customer")
	 private CustomerVO customer;
	 
	 @Column(name = "complaint_no")
	 private Long complaintNo;
	 
	 @Column(name = "customer_name")
	 private Long customerName;
	 
	 @Column(name = "item_code")
	 private Long itemCode;
	 
	 @Column(name = "item_description")
	 private String itemDescription;
	 
	 @Column(name = "root_cause_no")
	 private Long rootCauseNo;
	 
	 @Column(name = "root_cause_date")
	 private LocalDate rootCauseDate;
	 
	 @Column(name = "date_opened")
	 private LocalDate dateOpened;
	 
	 @Column(name = "target_date")
	 private LocalDate targetDate;
	 
	 
	    @Column(name = "remarks")
		private String remarks;
		
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
		private String screenName = "EIGHTDISCIPLINEENTRY";
		@Column(name = "screen_code")
		private String screenCode = "EDE";
		
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline1DetailVO> eightDiscipline1DetailVO = new ArrayList<>();
		
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline2DetailVO> eightDiscipline2DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline3DetailVO> eightDiscipline3DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline4DetailVO> eightDiscipline4DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline5DetailVO> eightDiscipline5DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline6DetailVO> eightDiscipline6DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline7DetailVO> eightDiscipline7DetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "eightDisciplineEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<EightDiscipline8DetailVO> eightDiscipline8DetailVO = new ArrayList<>();
		
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
