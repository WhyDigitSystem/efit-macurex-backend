package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "import_grn_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportGrnDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "import_grn_detailsgen")
	@SequenceGenerator(name = "import_grn_detailsgen", sequenceName = "import_grn_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "import_grn_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "uom")
	private UnitMasterVO uom;

	@ManyToOne
	@JoinColumn(name = "po_unit")
	private UnitMasterVO poUnit;

	@Column(name = "stock")
	private String stock;

	@Column(name = "inspectionable")
	private String inspectionable;

	@Column(name = "po_qty", precision = 10, scale = 2)
	private BigDecimal poQty;

	@Column(name = "balance_po_qty", precision = 10, scale = 2)
	private BigDecimal balancePoQty;

	@Column(name = "challan_qty", precision = 10, scale = 2)
	private BigDecimal challanQty;

	@Column(name = "received_qty", precision = 10, scale = 2)
	private BigDecimal receivedQty;

	@Column(name = "short_qty", precision = 10, scale = 2)
	private BigDecimal shortQty;

	@Column(name = "acpt_qty", precision = 10, scale = 2)
	private BigDecimal acptQty;

	@Column(name = "rej_qty", precision = 10, scale = 2)
	private BigDecimal rejQty;

	@Column(name = "fob_rate_fc", precision = 10, scale = 2)
	private BigDecimal fobRateFC;

	@Column(name = "fob_value_fc", precision = 10, scale = 2)
	private BigDecimal fobValueFC;

	@Column(name = "fob_value_inr", precision = 10, scale = 2)
	private BigDecimal fobValueINR;

	@Column(name = "freight", precision = 10, scale = 2)
	private BigDecimal freight;

	@Column(name = "freight_ind",precision = 10, scale = 2)
	private BigDecimal freightInd;

	@Column(name = "bcd_value_inr", precision = 10, scale = 2)
	private BigDecimal bcdValueINR;

	@Column(name = "cess_at_10", precision = 10, scale = 2)
	private BigDecimal cessAt10;

	@Column(name = "excise_cvd_igst", precision = 10, scale = 2)
	private BigDecimal exciseCvdIgst;

	@Column(name = "add_duty", precision = 10, scale = 2)
	private BigDecimal addDuty;

	@Column(name = "clearing_charge", precision = 10, scale = 2)
	private BigDecimal clearingCharge;

	@Column(name = "bank_charge", precision = 10, scale = 2)
	private BigDecimal bankCharge;

	@Column(name = "packing_charge", precision = 10, scale = 2)
	private BigDecimal packingCharge;

	@Column(name = "surcharge", precision = 10, scale = 2)
	private BigDecimal surcharge;

	@Column(name = "special_cost", precision = 10, scale = 2)
	private BigDecimal specialCost;

	@Column(name = "handling_charge", precision = 10, scale = 2)
	private BigDecimal handlingCharge;

	@Column(name = "total_value_fc", precision = 10, scale = 2)
	private BigDecimal totalValueFC;

	@Column(name = "total_value_inr", precision = 10, scale = 2)
	private BigDecimal totalValueINR;

	@Column(name = "landing_value", precision = 10, scale = 2)
	private BigDecimal landingValue;

	@Column(name = "landing_cost_inr", precision = 10, scale = 2)
	private BigDecimal landingCostINR;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "grn_basic_id")
	GrnVO grnVO;

}
