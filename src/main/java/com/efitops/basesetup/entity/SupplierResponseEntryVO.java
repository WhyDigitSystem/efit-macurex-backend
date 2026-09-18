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
@Table(name = "supplier_response_entry_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseEntryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_response_entry_basicgen")
	@SequenceGenerator(name = "supplier_response_entry_basicgen", sequenceName = "supplier_response_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "supplier_response_entry_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@Column(name = "complaint_no")
	private String complaintNo;
	
	@Column(name = "complaint_date")
	private String complaintDate;
	
	@Column(name = "product_no")
	private String productNo;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "supplier_no")
	private String supplierNo;
	
	@Column(name = "supplier_name")
	private String supplierName;
	
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
	private String screenName = "SUPPLIER RESPONSE  ENTRY";
	@Column(name = "screen_code")
	private String screenCode = "SRE";

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

	@OneToMany(mappedBy = "supplierResponseEntryVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<SupplierResponseEntryDetailsVO> supplierResponseEntryDetailsVO = new ArrayList<>();

	
}
