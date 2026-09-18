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
@Table(name = "vendor_complaint_entry_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorComplaintEntryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_complaint_entry_basicgen")
	@SequenceGenerator(name = "vendor_complaint_entry_basicgen", sequenceName = "vendor_complaint_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendor_complaint_entry_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "fg_item")
	private ItemMasterVO fgItem;

	@ManyToOne
	@JoinColumn(name = "supplier")
	private CustomerVO supplier;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "active")
	private boolean active;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "financial_year")
	private String FinancialYear;
	
	
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "VENDOR COMPLAINT ENTRY";
	@Column(name = "screen_code")
	private String screenCode = "VCE";

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

	@OneToMany(mappedBy = "vendorComplaintEntryVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<VendorComplaintDetailsVO> vendorComplaintDetailsVO = new ArrayList<>();

}
