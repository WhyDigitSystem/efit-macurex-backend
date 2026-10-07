package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "pre_delivery_inspection_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDeliveryInspectionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_delivery_inspection_basicgen")
	@SequenceGenerator(name = "pre_delivery_inspection_basicgen", sequenceName = "pre_delivery_inspection_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pre_delivery_inspection_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "transfer_slip_no")
	private String transferSlipNo;

	@Column(name = "transfer_slip_date")
	private LocalDate transferSlipDate;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "item_drawing_no")
	private String itemDrawingNo;

	@Column(name = "prod_sch_ord_no")
	private String prodSchOrdNo;

	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;

	@Column(name = "stock", precision = 10, scale = 3)
	private BigDecimal stock;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "lc_delivery_sch_no")
	private String lcDeliverySchNo;

	@Column(name = "po_no")
	private String poNo;

	@Column(name = "inv_no")
	private String invNo;

	@Column(name = "inv_date")
	private LocalDate invDate;

	@Column(name = "initial_plan_no")
	private String initialPlanNo;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "sch_ord_date")
	private LocalDate schOrdDate;

	@Column(name = "sch_qty", precision = 15, scale = 2)
	private BigDecimal schQty;

	@Column(name = "produced_qty", precision = 15, scale = 2)
	private BigDecimal producedQty;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "cust_part_no")
	private String custPartNo;

	@Column(name = "time")
	private LocalTime time = LocalTime.now();

	// Summary Details (Bottom Grid)
	@Column(name = "qty_inspected", precision = 15, scale = 2)
	private BigDecimal qtyInspected;

	@Column(name = "qty_passed", precision = 15, scale = 2)
	private BigDecimal qtyPassed;

	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "rej_qty", precision = 15, scale = 2)
	private BigDecimal rejQty;

	@ManyToOne
	@JoinColumn(name = "rejected_location")
	private LocationVO rejectedLocation;

	@Column(name = "reason_for_rejection")
	private String reasonForRejection;

	@Column(name = "scrap_qty", precision = 15, scale = 2)
	private BigDecimal scrapQty;

	@Column(name = "rework_qty", precision = 15, scale = 2)
	private BigDecimal reworkQty;

	@Column(name = "reason_for_rework")
	private String reasonForRework;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "inspected_by")
	private EmployeeMasterVO inspectedBy;

	@ManyToOne
	@JoinColumn(name = "checked_by")
	private EmployeeMasterVO checkedBy;

	// Audit fields
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

	@Column(name = "screen_name")
	private String screenName = "PreDeliveryInspection";

	@Column(name = "screen_code")
	private String screenCode = "PDI";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	// Grid Details
	@OneToMany(mappedBy = "preDeliveryInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PreDeliveryInspectionDetailsVO> inspectionDetails;

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