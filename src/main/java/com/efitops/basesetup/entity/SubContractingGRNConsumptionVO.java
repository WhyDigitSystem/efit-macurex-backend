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
@Table(name = "sub_contracting_grn_consumption")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNConsumptionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_contracting_grn_consumptiongen")
	@SequenceGenerator(name = "sub_contracting_grn_consumptiongen", sequenceName = "sub_contracting_grn_consumptionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sub_contracting_grn_consumption_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "outgoing_item")
	private ItemMasterVO outgoingItem;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "item_type")
	private String itemType;

	@Column(name = "bom_qty", precision = 15, scale = 5)
	private BigDecimal bomQty;

	@Column(name = "available_stock", precision = 15, scale = 5)
	private BigDecimal availableStock;

	@Column(name = "consumed_qty", precision = 15, scale = 5)
	private BigDecimal consumedQty;

	@Column(name = "scrap_item")
	private String scrapItem;

	@Column(name = "bom_scrap", precision = 15, scale = 5)
	private BigDecimal bomScrap;

	@Column(name = "scrap_qty", precision = 15, scale = 5)
	private BigDecimal scrapQty;

	@Column(name = "rate", precision = 15, scale = 5)
	private BigDecimal rate;

	@Column(name = "amount", precision = 15, scale = 5)
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "sub_contracting_grn_details_id")
	@JsonBackReference
	private SubContractingGRNDetailsVO subContractingGRNDetailsVO;
}