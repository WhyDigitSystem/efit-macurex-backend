package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grn_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grn_detailsgen")
	@SequenceGenerator(name = "grn_detailsgen", sequenceName = "grn_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grn_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "purchase_unit")
	private UnitMasterVO purchaseUnit;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@Column(name = "stock", precision = 10, scale = 2)
	private BigDecimal stock;

	@Column(name = "purchase_tolerance", precision = 10, scale = 2)
	private BigDecimal purchaseTolerance;

	@Column(name = "inspectionable")
	private String inspectionable;

	@Column(name = "manufactured_date")
	private LocalDate manufacturedDate;

	@Column(name = "po_rate", precision = 10, scale = 2)
	private BigDecimal poRate;

	@Column(name = "po_qty", precision = 10, scale = 2)
	private BigDecimal poQty;

	@ManyToOne
	@JoinColumn(name = "po_unit")
	private UnitMasterVO poUnit;

	@Column(name = "received_qty", precision = 10, scale = 2)
	private BigDecimal receivedQty;

	@Column(name = "store_stock", precision = 10, scale = 2)
	private BigDecimal storeStock;

	@Column(name = "pending_qty", precision = 10, scale = 2)
	private BigDecimal pendingQty;

	@ManyToOne
	@JoinColumn(name = "received_unit")
	private UnitMasterVO receivedUnit;

	@Column(name = "conversion_factor", precision = 10, scale = 2)
	private BigDecimal conversionFactor;

	@Column(name = "rec_qty_in_primary_unit", precision = 10, scale = 2)
	private BigDecimal recQtyInPrimaryUnit;

	@Column(name = "accept_qty", precision = 10, scale = 2)
	private BigDecimal acceptQty;

	@Column(name = "acc_qty_in_primary_unit", precision = 10, scale = 2)
	private BigDecimal accQtyInPrimaryUnit;

	@ManyToOne
	@JoinColumn(name = "acc_unit")
	private UnitMasterVO accUnit;

	@Column(name = "reject_qty", precision = 10, scale = 2)
	private BigDecimal rejectQty;

	@Column(name = "rej_qty_in_primary_unit", precision = 10, scale = 2)
	private BigDecimal rejQtyInPrimaryUnit;

	@Column(name = "excess_qty", precision = 10, scale = 2)
	private BigDecimal excessQty;

	@Column(name = "item_max_qty", precision = 10, scale = 2)
	private BigDecimal itemMaxQty;

	@Column(name = "tax_percentage", precision = 10, scale = 2)
	private BigDecimal taxPercentage;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "hsn_code")
	private String hsnCode;

	@Column(name = "tax_type")
	private String taxType;

	@Column(name = "sgst_rate", precision = 10, scale = 2)
	private BigDecimal sgstRate;

	@Column(name = "sgst_amount", precision = 10, scale = 2)
	private BigDecimal sgstAmount;

	@Column(name = "cgst_rate", precision = 10, scale = 2)
	private BigDecimal cgstRate;

	@Column(name = "cgst_amount", precision = 10, scale = 2)
	private BigDecimal cgstAmount;

	@Column(name = "igst_rate", precision = 10, scale = 2)
	private BigDecimal igstRate;

	@Column(name = "igst_amount", precision = 10, scale = 2)
	private BigDecimal igstAmount;

	@Column(name = "apportioned_cost", precision = 10, scale = 2)
	private BigDecimal apportionedCost;

	@Column(name = "insurance", precision = 10, scale = 2)
	private BigDecimal insurance;

	@Column(name = "bankchrg", precision = 10, scale = 2)
	private BigDecimal bankchrg;

	@Column(name = "lcost", precision = 10, scale = 2)
	private BigDecimal lcost;

	@Column(name = "landed_cost_rate", precision = 10, scale = 2)
	private BigDecimal landedCostRate;

	@Column(name = "landed_value", precision = 10, scale = 2)
	private BigDecimal landedValue;
	
	@Column(name = "hand_charge", precision = 10, scale = 2)
	private BigDecimal handCharge;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "grn_basic_id")
	GrnVO grnVO;
}
