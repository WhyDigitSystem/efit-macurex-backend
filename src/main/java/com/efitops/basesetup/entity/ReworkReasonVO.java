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
@Table(name = "rework_reason")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReworkReasonVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rework_reasongen")
	@SequenceGenerator(name = "rework_reasongen", sequenceName = "rework_reasonseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rework_reason_id")
	private Long id;

    @ManyToOne
    @JoinColumn(name = "reason")
	private ReasonMasterVO reason;

	@Column(name = "reason_description")
	private String reasonDescription;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;

	@Column(name = "time_per_qty", precision = 10, scale = 2)
	private BigDecimal timePerQty;

	@Column(name = "rework_prod_hrs", precision = 10, scale = 2)
	private BigDecimal reworkProdHrs;

	@Column(name = "rework_mc_cost", precision = 10, scale = 2)
	private BigDecimal reworkMcCost;

	@Column(name = "rework_labour_cost", precision = 10, scale = 2)
	private BigDecimal reworkLabourCost;

	@ManyToOne
	@JoinColumn(name = "production_entry_basic_id")
	@JsonBackReference
	private ProductionEntryVO productionEntryVO;
}
