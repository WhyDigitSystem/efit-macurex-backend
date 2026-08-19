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
@Table(name = "purchase_order_local_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderLocalTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_local_tax_detailsgen")
	@SequenceGenerator(name = "purchase_order_local_tax_detailsgen", sequenceName = "purchase_order_local_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_order_local_tax_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "purchase_order_basic_id")
	PurchaseOrderVO purchaseOrderVO;
}
