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
@Table(name = "issue_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class IssuesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_basicgen")
	@SequenceGenerator(name = "issue_basicgen", sequenceName = "issue_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "issue_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate=LocalDate.now();
	
    @ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	 
	@Column(name = "belongs_to")
	private String belongsTo;
	 
	@Column(name = "time")
	private LocalTime time;
	 
	@Column(name = "ref_no")
	private String refNo;
	  
	@Column(name = "ref_date")
	private LocalDate refDate;
	  
	@Column(name = "indent_no")
	private String indentNo;
	  
	@ManyToOne
	@JoinColumn(name = "issue_from")
    private LocationVO issueFrom;
	  
	@ManyToOne
	@JoinColumn(name = "issue_to")
    private LocationVO issueTo;
	
	@Column(name = "narration")
	private String narration;
	
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
	private String screenName = "ISSUES";
	@Column(name = "screen_code")
	private String screenCode = "ISU";
	
	
	@OneToMany(mappedBy = "issuesVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<IssuesDetailsVO> details = new ArrayList<>();

	
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
