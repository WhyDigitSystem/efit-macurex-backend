package com.efitops.basesetup.entity;

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
@Table(name = "docket_invoice_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docketinvoicedetailgen")
	@SequenceGenerator(name = "docketinvoicedetailgen", sequenceName = "docketinvoicedetailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "docket_invoice_detail_id")
    private Long id;
	
	@Column(name = "docket_no")
	private String docketNo;
	
	@Column(name = "docket_date")
	private LocalDate docketDate;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "no_of_qtr")
	private int noOfQty;
	
	@Column(name = "weight")
	private double weight;
	
	@Column(name = "total_value")
	private double totalValue;
	
	@Column(name = "cumulative_value")
	private Double cumulativeValue;
	
	@Column(name = "mode")
	private String mode;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JsonBackReference
	    @JoinColumn(name="docket_invoice_basic_id")
	    private DocketInvoiceVO docketInvoiceVO;
	
}
