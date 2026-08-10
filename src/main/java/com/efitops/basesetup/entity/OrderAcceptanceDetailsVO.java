package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "order_acceptance_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_acceptance_detailgen")
	@SequenceGenerator(name = "order_acceptance_detailgen", sequenceName = "order_acceptance_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "order_acceptance_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "customer_part_no")
	private String customerPartNo;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "tax_type")
	private String taxType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_percentage")
	private GSTRateMasterVO taxPercentage;

	@Column(name = "last_invoice_date")
	private LocalDate lastInvoiceDate;

	@Column(name = "quantity", precision = 10, scale = 2)
	private BigDecimal quantity;

	@Column(name = "quantity_rate", precision = 10, scale = 2)
	private BigDecimal quantityRate;

	@Column(name = "order_rate", precision = 10, scale = 2)
	private BigDecimal orderRate;

	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;

	@Column(name = "discount_percentage", precision = 10, scale = 2)
	private BigDecimal discountPercentage;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "sgst_rate", precision = 10, scale = 2)
	private BigDecimal sgstRate;

	@Column(name = "sgst_amount", precision = 10, scale = 2)
	private BigDecimal sgstAmount;

	@Column(name = "cgst_rate", precision = 10, scale = 2)
	private BigDecimal cgstRate;

	@Column(name = "cgst_amount", precision = 10, scale = 2)
	private BigDecimal cgstAmount;

	@Column(name = "igst_rate", precision = 10, scale = 2)
	private BigDecimal igstRate;

	@Column(name = "igst_amount", precision = 10, scale = 2)
	private BigDecimal igstAmount;

	@Column(name = "discount_amount", precision = 10, scale = 2)
	private BigDecimal discountAmount;

	@Column(name = "order_amount", precision = 10, scale = 2)
	private BigDecimal orderAmount;

	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "currency_name")
	private String currencyName;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "order_acceptance_basic_id")
	OrderAcceptanceVO orderAcceptanceVO;

}
