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
@Table(name = "rm_consumption_entry_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmConsumptionEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rm_consumption_entry_detailsgen")
	@SequenceGenerator(name = "rm_consumption_entry_detailsgen", sequenceName = "rm_consumption_entry_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rm_consumption_entry_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "consumption_as_per_bom_qty", precision = 10, scale = 2)
	private BigDecimal consumptionAsPerBomQty;

	@Column(name = "available_stock", precision = 10, scale = 2)
	private BigDecimal availableStock;

	@Column(name = "actual_consumed_qty", precision = 10, scale = 2)
	private BigDecimal actualConsumedQty;

	@Column(name = "wastage_qty", precision = 10, scale = 2)
	private BigDecimal wastageQty;

	@Column(name = "scrap_qty", precision = 10, scale = 2)
	private BigDecimal scrapQty;

	@Column(name = "total_consumed_qty", precision = 10, scale = 2)
	private BigDecimal totalConsumedQty;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	
	@Column(name = "consumed_qty", precision = 10, scale = 2)
	private BigDecimal consumedQty;

	@ManyToOne
	@JoinColumn(name = "consumption_entry_id")
	@JsonBackReference
	private ConsumptionEntryVO consumptionEntryVO;
}
