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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliverychallan_capital_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCapitalItemsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallan_capital_itemgen")
	@SequenceGenerator(name = "deliverychallan_capital_itemgen", sequenceName = "deliverychallan_capital_itemsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychallan_capital_items_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@ManyToOne
	@JoinColumn(name = "vendor")
	private CustomerVO vendor;

	@Column(name = "indent_no")
	private String indentNo;

	@ManyToOne
	@JoinColumn(name = "customer_location")
	private LocationVO customerLocation;

	@Column(name = "transport_name")
	private String transportName;

	@Column(name = "vehicle_no")
	private String vehicleNo;

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
	private String screenName = "D.C FOR CAPITAL ITEMS";

	@Column(name = "screen_code")
	private String screenCode = "DCCI";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@OneToMany(mappedBy = "deliveryChallanCapitalItemsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeliveryChallanCapitalItemsDetailsVO> details = new ArrayList<>();

	@Embedded
	private CreatedUpdatedDate commonDate;


	// Generate getters and setters
}