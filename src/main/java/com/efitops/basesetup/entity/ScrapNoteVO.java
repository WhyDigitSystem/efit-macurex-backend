package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "scrap_note_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_note_basicgen")
	@SequenceGenerator(name = "scrap_note_basicgen", sequenceName = "scrap_note_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "scrap_note_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "time")
	private LocalTime time = LocalTime.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;

	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;

	@ManyToOne
	@JoinColumn(name = "fg_part")
	private ItemMasterVO fgPart;

	@Column(name = "sch_order_no")
	private String schOrderNo;

	@ManyToOne
	@JoinColumn(name = "bom")
	private BillOfMaterialVO bom;

	@ManyToOne
	@JoinColumn(name = "scrap_part")
	private ItemMasterVO scrapPart;

	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;

	@ManyToOne
	@JoinColumn(name = "authorised_by")
	private EmployeeMasterVO authorisedBy;

	@ManyToOne
	@JoinColumn(name = "scrap_id")
	private ListOfValuesDetailsVO scrapId;

	@Column(name = "total_scrap_value", precision = 10, scale = 2)
	private BigDecimal totalScrapValue;

	@Column(name = "pm_approval")
	private String pmApproval;

	@Column(name = "quality_approval")
	private String qualityApproval;

	@Column(name = "store_approval")
	private String storeApproval;

	@Column(name = "narration")
	private String narration;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "ScrapNote";

	@Column(name = "screen_code")
	private String screenCode = "SN";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "scrapNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ScrapNoteDetailsVO> scrapNoteDetailsVO;

	@OneToMany(mappedBy = "scrapNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ScrapNoteReasonDetailsVO> scrapNoteReasonDetailsVO; 

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
