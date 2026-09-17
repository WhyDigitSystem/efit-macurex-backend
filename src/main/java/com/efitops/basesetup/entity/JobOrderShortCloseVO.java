package com.efitops.basesetup.entity;

import java.time.LocalDate;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_order_short_close")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderShortCloseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_order_short_closegen")
	@SequenceGenerator(name = "job_order_short_closegen", sequenceName = "job_order_short_closeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "job_order_short_close_id")
	private Long id;

	// Customer
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;

	// Short Close No
	@Column(name = "doc_id")
	private String docId;

	// Date
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	// Job Order No
	@Column(name = "job_order_no")
	private String jobOrderNo;
	
	// Reference For Short Close
	@Column(name = "reference_for_sc")
	private String referenceForSc;

	// Common Fields
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "JOB ORDER SHORT CLOSE";

	@Column(name = "screen_code")
	private String screenCode = "JOSC";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	// Branch
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	// Child Details
	@OneToMany(mappedBy = "jobOrderShortCloseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<JobOrderShortCloseDetailsVO> jobOrderShortCloseDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}