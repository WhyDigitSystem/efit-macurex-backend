package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "sub_contracting_grn_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_contracting_grn_detailsgen")
	@SequenceGenerator(name = "sub_contracting_grn_detailsgen", sequenceName = "sub_contracting_grn_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sub_contracting_grn_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "incoming_item")
	private ItemMasterVO incomingItem;

	@Column(name = "stock", precision = 15, scale = 5)
	private BigDecimal stock;

	@Column(name = "tolerance", precision = 15, scale = 5)
	private BigDecimal tolerance;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@Column(name = "job_order_no")
	private String jobOrderNo;

	@Column(name = "job_order_qty", precision = 15, scale = 5)
	private BigDecimal jobOrderQty;

	@Column(name = "job_order_rate", precision = 15, scale = 5)
	private BigDecimal jobOrderRate;

	@Column(name = "gate_pass_qty", precision = 15, scale = 5)
	private BigDecimal gatePassQty;

	@Column(name = "inspectionable")
	private String inspectionable;

	@Column(name = "pending_qty", precision = 15, scale = 5)
	private BigDecimal pendingQty;

	@Column(name = "received_qty", precision = 15, scale = 5)
	private BigDecimal receivedQty;

	@Column(name = "excess_qty", precision = 15, scale = 5)
	private BigDecimal excessQty;

	@Column(name = "qty_in_primary_unit", precision = 15, scale = 5)
	private BigDecimal qtyInPrimaryUnit;

	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;

	@Column(name = "accepted_qty", precision = 15, scale = 5)
	private BigDecimal acceptedQty;

	@Column(name = "acc_qty_in_primary_unit", precision = 15, scale = 5)
	private BigDecimal accQtyInPrimaryUnit;

	@Column(name = "rejected_qty", precision = 15, scale = 5)
	private BigDecimal rejectedQty;

	@Column(name = "rej_qty_in_primary_unit", precision = 15, scale = 5)
	private BigDecimal rejQtyInPrimaryUnit;

	@Column(name = "amount", precision = 15, scale = 5)
	private BigDecimal amount;

	@Column(name = "sgst_rate", precision = 15, scale = 5)
	private BigDecimal sgstRate;

	@Column(name = "sgst_amount", precision = 15, scale = 5)
	private BigDecimal sgstAmount;

	@Column(name = "cgst_rate", precision = 15, scale = 5)
	private BigDecimal cgstRate;

	@Column(name = "cgst_amount", precision = 15, scale = 5)
	private BigDecimal cgstAmount;

	@Column(name = "igst_rate", precision = 15, scale = 5)
	private BigDecimal igstRate;

	@Column(name = "igst_amount", precision = 15, scale = 5)
	private BigDecimal igstAmount;

	@ManyToOne
	@JoinColumn(name = "sub_contracting_grn_id")
	@JsonBackReference
	private SubContractingGRNVO subContractingGRNVO;

	@OneToMany(mappedBy = "subContractingGRNDetailsVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<SubContractingGRNConsumptionVO> consumption = new ArrayList<>();
}