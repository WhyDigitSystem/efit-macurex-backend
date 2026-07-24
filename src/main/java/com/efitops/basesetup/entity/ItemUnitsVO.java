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
@Table(name = "itemunits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUnitsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemunitsgen")
	@SequenceGenerator(name = "itemunitsgen", sequenceName = "itemunitsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemunits_id",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "purchase_unit", precision = 10, scale = 2)
	private BigDecimal purchaseUnit;

	@Column(name = "selling_unit", precision = 10, scale = 2)
	private BigDecimal sellingUnit;

	@Column(name = "pricing_unit", precision = 10, scale = 2)
	private BigDecimal pricingUnit;

	@Column(name = "secondary_unit", precision = 10, scale = 2)
	private BigDecimal secondaryUnit;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "itemmaster_id")
	ItemMasterVO itemMasterVO;
}
