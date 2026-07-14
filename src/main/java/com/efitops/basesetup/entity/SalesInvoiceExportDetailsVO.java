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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesinvoiceexportdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceExportDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesinvoiceexportdetailsgen")
	@SequenceGenerator(name = "salesinvoiceexportdetailsgen", sequenceName = "salesinvoiceexportdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesinvoiceexportdetailsid")
	private Long id;
	@Column(name = "item")
	private String item;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "units")
	private String units;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;
	@Column(name = "discountamount", precision = 10, scale = 2)
	private BigDecimal discountAmount;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;

	@ManyToOne
	@JoinColumn(name = "salesinvoiceexportid")
	@JsonBackReference
	private SalesInvoiceExportVO salesInvoiceExportVO;

}
