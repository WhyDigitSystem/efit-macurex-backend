package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_return_tax_details")
public class PurchaseReturnTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_return_tax_detailsgen")
	@SequenceGenerator(name = "purchase_return_tax_detailsgen", sequenceName = "purchase_return_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_return_tax_details_id")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;

	@Column(name = "accepted_qty_amount")
	private BigDecimal acceptedQtyAmount;

	@Column(name = "revised_amount")
	private BigDecimal revisedAmount;

	@ManyToOne
	@JoinColumn(name = "purchase_return_basic_id")
	@JsonBackReference
	private PurchaseReturnVO purchaseReturnVO;

}