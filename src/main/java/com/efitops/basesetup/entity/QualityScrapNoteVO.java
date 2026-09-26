package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "quality_scrap_note_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityScrapNoteVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_scrap_note_basicgen")
	@SequenceGenerator(name = "quality_scrap_note_basicgen", sequenceName = "quality_scrap_note_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quality_scrap_note_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate .now();
	
	@Column(name = "time")
	private LocalTime time;
	
	@ManyToOne
	@JoinColumn(name = "belongs_to")
	private ListOfValuesDetailsVO belongsTo;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation ;

	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation ;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy ;
	
	@ManyToOne
	@JoinColumn(name = "authorized_by")
	private EmployeeMasterVO authorizedBy ;
	
	@Column(name = "total_scrap_value")
	private BigDecimal totalScrapValue;
	
	@Column(name = "quality_approval")
	private String qualityApproval;
	
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
	private String screenCode = "QSN";

	@Column(name = "screen_name")
	private String screenName = "QUALITY SCRAP NOTE";
	
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
	
	@OneToMany(mappedBy = "qualityScrapNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QualityScrapNoteDetailsVO> qualityScrapNoteDetailsVO = new ArrayList<>();
}
