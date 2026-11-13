package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inward_inspection_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inward_inspection_detailsgen")
	@SequenceGenerator(name = "inward_inspection_detailsgen", sequenceName = "inward_inspection_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inward_inspection_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "inspection")
	private String inspection;

	@Column(name = "stk")
	private String stk;

	@Column(name = "drawing_no")
	private String drawingNo;

	@Column(name = "order_qty", precision = 10, scale = 2)
	private BigDecimal orderQty;

	@ManyToOne
	@JoinColumn(name = "purchase_unit")
	private UnitMasterVO purchaseUnit;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@Column(name = "received_qty", precision = 10, scale = 2)
	private BigDecimal receivedQty;

	@ManyToOne
	@JoinColumn(name = "received_unit")
	private UnitMasterVO receivedUnit;

	@Column(name = "accept_qty", precision = 10, scale = 2)
	private BigDecimal acceptQty;

	@ManyToOne
	@JoinColumn(name = "received_location")
	private LocationVO receivedLocation;

	@ManyToOne
	@JoinColumn(name = "accept_unit")
	private UnitMasterVO acceptUnit;

	@Column(name = "inspection_report_received")
	private String inspectionReportReceived;

	@Column(name = "tc_received")
	private String tcReceived;

	@Column(name = "batch_no")
	private String batchNo;

	@Column(name = "qty_acc_on_devtn", precision = 10, scale = 2)
	private BigDecimal qtyAccOnDevtn;

	@Column(name = "acc_qty_after_segn", precision = 10, scale = 2)
	private BigDecimal accQtyAfterSegn;

	@Column(name = "rework_qty", precision = 10, scale = 2)
	private BigDecimal reworkQty;

	@ManyToOne
	@JoinColumn(name = "rework_location")
	private LocationVO reworkLocation;

	@Column(name = "tot_acc_qty", precision = 10, scale = 2)
	private BigDecimal totAccQty;

	@Column(name = "conversion_factor", precision = 10, scale = 2)
	private BigDecimal conversionFactor;

	@Column(name = "total_acc_qty_in_primary_unit", precision = 10, scale = 2)
	private BigDecimal totalAccQtyInPrimaryUnit;

	@ManyToOne
	@JoinColumn(name = "rework_unit")
	private UnitMasterVO reworkUnit;

	@Column(name = "reject_qty", precision = 10, scale = 2)
	private BigDecimal rejectQty;

	@ManyToOne
	@JoinColumn(name = "rejected_location")
	private LocationVO rejectedLocation;

	@Column(name = "reason")
	private String reason;

	@ManyToOne
	@JoinColumn(name = "reject_unit")
	private UnitMasterVO rejectUnit;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "total_received_qty", precision = 10, scale = 2)
	private BigDecimal totalReceivedQty;

	@Column(name = "sample_size", precision = 10, scale = 2)
	private BigDecimal sampleSize;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "inward_inspection_basic_id")
	InwardInspectionVO inwardInspectionVO;

	@OneToMany(mappedBy = "inwardInspectionDetailsVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<InwardInspectionMeasurementsVO> inwardInspectionMeasurementsVO = new ArrayList<>();
}
