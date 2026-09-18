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
@Table(name = "production_transfer_slip_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_transfer_slip_detailsgen")
	@SequenceGenerator(name = "production_transfer_slip_detailsgen", sequenceName = "production_transfer_slip_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_transfer_slip_detailsid")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "stock", precision = 10, scale = 2)
	private BigDecimal stock;

	@Column(name = "bom_qty", precision = 10, scale = 2)
	private BigDecimal bomQty;

	@Column(name = "input_qty", precision = 10, scale = 2)
	private BigDecimal inputQty;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "value", precision = 10, scale = 2)
	private BigDecimal value;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@ManyToOne
	@JoinColumn(name = "scrap")
	private ListOfValuesDetailsVO scrap;
	
	
	@Column(name = "scrap_qty", precision = 10, scale = 2)
	private BigDecimal scrapQty;

	@Column(name = "scrap_total", precision = 10, scale = 2)
	private BigDecimal scrapTotal;

	@ManyToOne
	@JoinColumn(name = "production_transfer_slip_basicid")
	@JsonBackReference
	private ProductionTransferSlipVO productionTransferSlipVO;
}
