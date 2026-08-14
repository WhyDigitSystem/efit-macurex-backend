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
@Table(name = "proforma_invoice_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProformaInvoiceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proforma_invoice_detailgen")
	@SequenceGenerator(name = "proforma_invoice_detailgen", sequenceName = "proforma_invoice_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "proforma_invoice_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "tax_percentage", precision = 10, scale = 2)
	private BigDecimal taxPercentage;

	@Column(name = "despatch_qty", precision = 10, scale = 2)
	private BigDecimal despatchQty;	

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;


	@Column(name = "order_rate", precision = 10, scale = 2)
	private BigDecimal orderRate;


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
	@JoinColumn(name = "proforma_invoice_basic_id")
	ProformaInvoiceVO proformaInvoiceVO;

}
