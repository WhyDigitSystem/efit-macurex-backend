package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "deliverychallan_subcontracting")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallan_subcontractinggen")
	@SequenceGenerator(name = "deliverychallan_subcontractinggen", sequenceName = "deliverychallan_subcontractingseq", allocationSize = 1, initialValue = 1000000001)
	@Column(name = "deliverychallan_subcontracting_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "vendor")
	private CustomerVO vendor;

	@ManyToOne
	@JoinColumn(name = "party_location")
	private LocationVO partyLocation;

	@Column(name = "job_order_no")
	private String jobOrderNo;

	@ManyToOne
	@JoinColumn(name = "incoming_item")
	private ItemMasterVO incomingItem;

	@ManyToOne
	@JoinColumn(name = "transport_name")
	private TransportMasterVO transportName;

	@Column(name = "vehicle_no")
	private String vehicleNo;

	@ManyToOne
	@JoinColumn(name = "sfg_bom_id")
	private BomVO sfgBomId;

	@Column(name = "qty")
	private BigDecimal qty;

	@Column(name = "time_of_issue")
	private String timeOfIssue;

	@Column(name = "dc_type")
	private String dcType;

	@Column(name = "approval_by_stores")
	private String approvalByStores;

	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;
	
	@Column(name = "remarks")
	private String remarks;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_code")
	private String screenCode = "SCDC";

	@Column(name = "screen_name")
	private String screenName = "D.C FOR SUB CONTRACTING";

	@OneToMany(mappedBy = "deliveryChallanSubcontracting", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeliveryChallanSubcontractingDetailsVO> details = new ArrayList<>();

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