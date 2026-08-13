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
@Table(name = "other_sales_inv_tax_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherSalesInvoiceTaxDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "other_sales_inv_tax_detailgen")
	@SequenceGenerator(name = "other_sales_inv_tax_detailgen", sequenceName = "other_sales_inv_tax_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "other_sales_inv_tax_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "accepted_qty_amount", precision = 10, scale = 2)
	private BigDecimal acceptedQtyAmount;

	@Column(name = "revised_amount", precision = 10, scale = 2)
	private BigDecimal revisedAmount;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "other_sales_inv_basic_id")
	OtherSalesInvoiceVO otherSalesInvoiceVO;

}
