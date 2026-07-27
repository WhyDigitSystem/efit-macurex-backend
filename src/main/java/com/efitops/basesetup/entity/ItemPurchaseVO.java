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
@Table(name = "itempurchase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itempurchasegen")
	@SequenceGenerator(name = "itempurchasegen", sequenceName = "itempurchaseseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itempurchase_id",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "default_supplier")
	private String defaultSupplier;

	@Column(name = "alternate_supplier")
	private String alternateSupplier;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@Column(name = "purchase_tolerance", precision = 10, scale = 2)
	private BigDecimal purchaseTolerance;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "landed_cost_rate", precision = 10, scale = 2)
	private BigDecimal landedCostRate;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchVO branch;

	@Column(name = "tool_owner")
	private String toolOwner;

	@Column(name = "tool_no")
	private String toolNo;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "itemmaster_id")
//	ItemMasterVO itemMasterVO;

}
