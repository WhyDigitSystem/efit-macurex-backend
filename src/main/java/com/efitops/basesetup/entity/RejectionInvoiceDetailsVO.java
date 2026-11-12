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
@Table(name = "rejection_invoice_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectionInvoiceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejection_invoice_detailgen")
	@SequenceGenerator(name = "rejection_invoice_detailgen", sequenceName = "rejection_invoice_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rejection_invoice_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "tax_type")
	private String taxType;

	@Column(name = "tax_percentage", precision = 10, scale = 2)
	private BigDecimal taxPercentage;

	@Column(name = "tariff_no")
	private String tariffNo;

	@Column(name = "stock")
	private String stock;

	@Column(name = "sales_order_contract_no")
	private String salesOrderContractNo;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;

	@Column(name = "no_of_packages", precision = 10, scale = 2)
	private BigDecimal noOfPackages;

	@Column(name = "package_type")
	private String packageType;

	@Column(name = "order_rate", precision = 10, scale = 2)
	private BigDecimal orderRate;

	@Column(name = "rate_in_selected_currency", precision = 10, scale = 2)
	private BigDecimal rateInSelectedCurrency;

	@Column(name = "amt_in_selected_currency", precision = 10, scale = 2)
	private BigDecimal amtInSelectedCurrency;

	@Column(name = "amount_in_rs", precision = 10, scale = 2)
	private BigDecimal amountInRs;

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

	@Column(name = "hsn_code")
	private String hsnCode;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "rejection_invoice_basic_id")
	RejectionInvoiceVO rejectionInvoiceVO;

}
