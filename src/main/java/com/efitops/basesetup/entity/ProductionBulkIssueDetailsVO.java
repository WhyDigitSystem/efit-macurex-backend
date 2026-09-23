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
@Table(name = "production_bulk_issue_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_bulk_issue_detailsgen")
	@SequenceGenerator(name = "production_bulk_issue_detailsgen", sequenceName = "production_bulk_issue_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_bulk_issue_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit; // Unit

	@Column(name = "available_qty", precision = 10, scale = 3)
	private BigDecimal availableQty;

	@Column(name = "ind_req_qty", precision = 10, scale = 3)
	private BigDecimal indReqQty;

	@Column(name = "ind_pend_qty", precision = 10, scale = 3)
	private BigDecimal indPendQty;

	@Column(name = "issue_qty", precision = 10, scale = 3)
	private BigDecimal issueQty;

	@Column(name = "rate", precision = 10, scale = 3)
	private BigDecimal rate;

	@Column(name = "amount", precision = 10, scale = 3)
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "production_bulk_issue_basic_id")
	@JsonBackReference
	private ProductionBulkIssueVO productionBulkIssueVO;
}