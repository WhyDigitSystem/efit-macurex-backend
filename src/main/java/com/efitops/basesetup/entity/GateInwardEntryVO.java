package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gate_inward_entry_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateInwardEntryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gate_inward_entry_basicgen")
	@SequenceGenerator(name = "gate_inward_entry_basicgen", sequenceName = "gate_inward_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gate_inward_entry_basic_id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name  = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "doc_type")
	private String docType;
	
	@Column(name = "modvat_copy_received")
	private String modvatCopyReceived;
	
	@Column(name = "supplier_invoice_number")
	private String supplierInvoiceNumber;
	
	@Column(name = "supplier_invoice_date")
	private LocalDate supplierInvoiceDate;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "time_of_entry")
	private String timeOfEntry;
	

	@Column(name = "active")
	private boolean active;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updated_By;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_code", length = 10)
	private String screenCode = "GIE";
	@Column(name = "screen_name", length = 30)
	private String screenName = "Gate Inward Entry";

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
