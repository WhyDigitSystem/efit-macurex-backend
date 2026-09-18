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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill_charges_summary")
public class PurchaseBillChargesSummaryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_bill_charges_summarygen")
	@SequenceGenerator(name = "purchase_bill_charges_summarygen", sequenceName = "purchase_bill_charges_summaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_bill_charges_summary_id")
	private Long id;

	@Column(name = "total_freight")
	private BigDecimal totalFreight;

	@Column(name = "total_qty")
	private BigDecimal totalQty;

	@Column(name = "basic_value")
	private BigDecimal basicValue;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "amount_in_words")
	private String amountInWords;

	@Column(name = "entry_tax_applicable")
	private boolean entryTaxApplicable;

	@Column(name = "narration")
	private String narration;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@ManyToOne
	@JoinColumn(name = "purchasebill_id")
	@JsonBackReference
	private PurchaseBillVO purchaseBillVO;

}
