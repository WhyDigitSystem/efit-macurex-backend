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
@Table(name = "fg_transfer_slip_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FGTransferSlipDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fg_transfer_slip_detailsgen")
	@SequenceGenerator(name = "fg_transfer_slip_detailsgen", sequenceName = "fg_transfer_slip_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "fg_transfer_slip_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "bom_qty", precision = 10, scale = 2)
	private BigDecimal bomQty;

	@Column(name = "available_stock", precision = 10, scale = 2)
	private BigDecimal availableStock;

	@Column(name = "consumption_as_per_bom", precision = 10, scale = 2)
	private BigDecimal consumptionAsPerBom;

	@Column(name = "wastage_qty", precision = 10, scale = 2)
	private BigDecimal wastageQty;

	@Column(name = "consumed_qty", precision = 10, scale = 2)
	private BigDecimal consumedQty;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "value", precision = 10, scale = 2)
	private BigDecimal value;

	@Column(name = "scrap_id")
	private String scrapId;

	@Column(name = "scrap_qty", precision = 10, scale = 2)
	private BigDecimal scrapQty;

	@ManyToOne
	@JoinColumn(name = "scrap_unit")
	private UnitMasterVO scrapUnit;

	@Column(name = "scrap_total", precision = 10, scale = 2)
	private BigDecimal scrapTotal;

	@ManyToOne
	@JoinColumn(name = "fg_transfer_slip_basic_id")
	@JsonBackReference
	private FgTransferSlipVO fgTransferSlipVO;
}