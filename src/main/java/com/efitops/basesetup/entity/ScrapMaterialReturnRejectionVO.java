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
@Table(name = "scrap_material_return_rejection_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_material_return_rejection_basicgen")
	@SequenceGenerator(name = "scrap_material_return_rejection_basicgen", sequenceName = "scrap_material_return_rejection_basiceq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "scrap_material_return_rejection_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "entry_for")
	private ListOfValuesDetailsVO entryFor;
	
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private CustomerVO vendorId;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;
	
	@ManyToOne
	@JoinColumn(name = "vendor_location")
	private LocationVO vendorLocation;
	
	@Column(name = "entry_type")
	private String entryType;
	
	@Column(name = "doc_no")
	private String docNo;
	
	@Column(name = "document_date")
	private LocalDate documentDate ;
	
	@Column(name = "approval_by_qc")
	private String approvalByQc;
	
	@Column(name = "reason_for_rejection")
	private String reasonForRejection;
	
	@Column(name = "approval_by_purchase")
	private String approvalByPurchase;
	
	
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
	private String screenCode = "SMRR";

	@Column(name = "screen_name")
	private String screenName = "SCRAP MATERIAL RETURN REJECTION";
	
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
	
	@OneToMany(mappedBy = "scrapMaterialReturnRejectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ScrapMaterialReturnRejectionDetailsVO> scrapMaterialReturnRejectionDetailsVO = new ArrayList<>();
	
	
	

}
