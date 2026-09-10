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
@Table(name = "production_schedule_order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionScheduleOrderDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_schedule_order_detailsgen")
	@SequenceGenerator(name = "production_schedule_order_detilsgen", sequenceName = "production_schedule_order_detilsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_schedule_order_detils_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "bom_qty", precision = 10, scale = 2)
	private BigDecimal bomQty;

	@Column(name = "qty_required", precision = 10, scale = 2)
	private BigDecimal qtyRequired;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@ManyToOne
	@JoinColumn(name = "scrap_unit")
	private UnitMasterVO scrapUnit;

	@Column(name = "scrap_qty", precision = 10, scale = 2)
	private BigDecimal scrapQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "production_schedule_order_basic_id")
	ProductionScheduleOrderVO productionScheduleOrderVO;

}
