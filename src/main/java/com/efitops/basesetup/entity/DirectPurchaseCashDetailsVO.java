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
@Table(name = "direct_purchase_cash_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectPurchaseCashDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direct_purchase_cash_detailsgen")
	@SequenceGenerator(name = "direct_purchase_cash_detailsgen", sequenceName = "direct_purchase_cash_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "direct_purchase_cash_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "hsn_code")
	private String hsnCode;

	@Column(name = "tax_type")
	private String taxType;

	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "dc_qty", precision = 10, scale = 2)
	private BigDecimal dcQty;

	@Column(name = "received_qty", precision = 18, scale = 2)
	private BigDecimal receivedQty;

	@Column(name = "rate", precision = 18, scale = 2)
	private BigDecimal rate;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "cgst_rate", precision = 10, scale = 2)
	private BigDecimal cgstRate;

	@Column(name = "cgst_amount", precision = 18, scale = 2)
	private BigDecimal cgstAmount;

	@Column(name = "sgst_rate", precision = 10, scale = 2)
	private BigDecimal sgstRate;

	@Column(name = "sgst_amount", precision = 18, scale = 2)
	private BigDecimal sgstAmount;

	@Column(name = "igst_rate", precision = 10, scale = 2)
	private BigDecimal igstRate;

	@Column(name = "igst_amount", precision = 18, scale = 2)
	private BigDecimal igstAmount;


	@ManyToOne
	@JoinColumn(name = "direct_purchase_basic_id")
	@JsonBackReference
	private DirectPurchaseVO directPurchaseVO;

}
