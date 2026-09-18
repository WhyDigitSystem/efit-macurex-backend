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
@Table(name = "material_indent_for_production_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialIndentForProductionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_indent_for_production_detailsgen")
	@SequenceGenerator(name = "material_indent_for_production_detailsgen", sequenceName = "material_indent_for_production_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "material_indent_for_production_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "sch_qty", precision = 10, scale = 2)
	private BigDecimal schQty;

	@Column(name = "stock_available", precision = 10, scale = 2)
	private BigDecimal stockAvailable;

	@Column(name = "required_qty", precision = 10, scale = 2)
	private BigDecimal requiredQty;

	@ManyToOne
	@JoinColumn(name = "material_indent_for_production_id")
	@JsonBackReference
	private MaterialIndentForProductionVO materialIndentForProductionVO;

}
