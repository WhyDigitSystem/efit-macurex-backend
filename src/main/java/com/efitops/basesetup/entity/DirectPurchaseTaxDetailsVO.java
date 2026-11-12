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
@Table(name = "direct_purchase_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectPurchaseTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direct_purchase_tax_detailsgen")
	@SequenceGenerator(name = "direct_purchase_tax_detailsgen", sequenceName = "direct_purchase_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "direct_purchase_tax_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;

	@Column(name = "accepted_qty_amount", precision = 18, scale = 2)
	private BigDecimal acceptedQtyAmount;

	@Column(name = "revised_amount", precision = 18, scale = 2)
	private BigDecimal revisedAmount;

	@Column(name = "tax_id")
	private String taxId;

	@ManyToOne
	@JoinColumn(name = "direct_purchase_basic_id")
	@JsonBackReference
	private DirectPurchaseVO directPurchaseVO;

}