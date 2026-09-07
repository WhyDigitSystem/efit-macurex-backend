package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "engineering_deviation_request_basic") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringDeviationRequestVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engineering_deviation_request_basicgen")
	@SequenceGenerator(name = "engineering_deviation_request_basicgen", sequenceName = "engineering_deviation_request_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "engineering_deviation_request_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate  = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "to_department")
	private DepartmentVO toDepartment;
	
	@ManyToOne
	@JoinColumn(name = "deviation_requested_by")
	private EmployeeMasterVO deviationRequestedBy;
	
	@Column(name = "part_description")
	private String partDescription;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "quantity_received")
	private BigDecimal quantityReceived;
	
	@Column(name = "supplier")
	private String supplier;
	
	@ManyToOne
	@JoinColumn(name = "deviation_requist_approved_by")
	private EmployeeMasterVO deviationRequistApprovedBy;
	
	@Column(name = "part_no")
	private String partNo;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "description_of_nc")
	private String descriptionOfNC;
	
	@Column(name = "reason_for_deviation_request")
	private String reasonForDeviationRequest;
	
	@Column(name = "action_on_nc")
	private String actionOnNC;
	
	@Column(name = "deviation_period")
	private String deviationPeriod;
	
	@ManyToOne
	@JoinColumn(name = "responsible_for_name")
	private EmployeeMasterVO responsibleForName;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@Column(name = "will_the__nc_affect_the_fit")
	private String willTheNCAffectTheFit;
	
	@Column(name = "will_the__nc_affect_the_form")
	private String willTheNCAffectTheForm;
	
	@Column(name = "will_the__nc_affect_the_function")
	private String willTheNCAffectTheFunction;
	
	@Column(name = "will_the__nc_affect_the_safety")
	private String willTheNCAffectTheSafety;
	
	@Column(name = "nature_of_the_deviation_request")
	private String natureOfTheDeviationRequest;
	
	@Column(name = "to_be_intimated_to_customer_and_action_on_customer_feed_back")
	private String toBeIntimatedToCustomerAndActionOnCustomerFeedBack;
	
	@Column(name = "note")
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "production_mgr")
	private EmployeeMasterVO productionMgr;
	
	@Column(name = "production_mgr_disposition")
	private String productionMgrDisposition;
	
	@ManyToOne
	@JoinColumn(name = "quality_mgr")
	private EmployeeMasterVO qualityMgr;
	
	@Column(name = "quality_mgr_disposition")
	private String qualityMgrDisposition;
	
	@ManyToOne
	@JoinColumn(name = "tdc_mgr")
	private EmployeeMasterVO tDCMgr;
	
	@Column(name = "tdc_mgr_disposition")
	private String tdcMgrDisposition;
	
	@ManyToOne
	@JoinColumn(name = "director_technical")
	private EmployeeMasterVO directorTechnical;
	
	@Column(name = "director_technical_disposition")
	private String directorTechnicalDisposition;
	
	@ManyToOne
	@JoinColumn(name = "pur_mgr")
	private EmployeeMasterVO purMgr;
	
	@Column(name = "pur_mgr_disposition")
	private String purMgrDisposition;
	
	@Column(name = "customer_intimation_mode_and_reference")
	private String CustomerIntimationModeAndReference;
	
	@Column(name = "customer_feed_back")
	private String customerFeedBack;
	
	@Column(name = "customer_feed_back_mode_and_reference")
	private String customerFeedBackModeAndReference;
	
	@Column(name = "decision")
	private String decision;
	

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
	private String screenName = "ENGINEERING DEVIATION REQUEST";
	@Column(name = "screen_code")
	private String screenCode = "EDR";

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
	
	@OneToMany(mappedBy = "engineeringDeviationRequestVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<EngineeringDeviationAttachmentVO> engineeringDeviationAttachmentVO = new ArrayList<>();
	
}
