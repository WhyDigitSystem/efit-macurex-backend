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
@Table(name = "physical_stock_reconcilation_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalStockReConcilationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physical_stock_reconcilation_basicgen")
	@SequenceGenerator(name = "physical_stock_reconcilation_basicgen", sequenceName = "physical_stock_reconcilation_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "physical_stock_reconcilation_basic_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "location_type")
	private ListOfValuesDetailsVO locationType;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;

	@Column(name = "time")
	private String time;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "ref_date")
	private LocalDate refDate;

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;

	@Column(name = "narration")
	private String narration;

	@Column(name = "approved_by_pm")
	private String approvedByPM;

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
	private String screenCode = "PSRC";

	@Column(name = "screen_name")
	private String screenName = "PHYSICAL STOCK RECONCILATION ";

    @OneToMany(mappedBy = "physicalStockReConcilationVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PhysicalStockReConcilationDetailsVO> physicalStockReConcilationDetailsVO = new ArrayList<>();

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
