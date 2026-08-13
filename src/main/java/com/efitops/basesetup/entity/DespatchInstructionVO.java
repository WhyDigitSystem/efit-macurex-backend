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
@Table(name = "despatch_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "despatch_basicgen")
	@SequenceGenerator(name = "despatch_basicgen", sequenceName = "despatch_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "despatch_basic_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate;

	@ManyToOne
	@JoinColumn(name = "custumer")
	private CustomerVO customer;

	@Column(name = "schdule_no")
	private String schduleNo;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "schdule_date")
	private String schduleDate;

	@ManyToOne
	@JoinColumn(name = "location_name")
	private LocationVO location;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "mode_of_transport")
	private String modeOfTransport;

	@Column(name = "net_weight")
	private double netWeight;

	@Column(name = "gross_weight")
	private double grossWeight;

	@Column(name = "delivery_instructions")
	private String deliveryInstructions;

	@Column(name = "Consignee")
	private String Consignee;

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
	private String screenCode = "DI";
	@Column(name = "screen_name", length = 30)
	private String screenName = "Despatch Instruction";

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

	@OneToMany(mappedBy = "despatchInstructionVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<DespatchInstructionDetailsVO> details = new ArrayList<>();

}
