package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "salesitemparticulars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesItemParticularsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesitemparticularsgen")
	@SequenceGenerator(name = "salesitemparticularsgen", sequenceName = "salesitemparticularsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesitemparticularsid")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "workorderno")
	private String workOrderNo;
	@Column(name = "duedate")
	private LocalDate dueDate;
	@Column(name = "unitprice", precision = 10, scale = 2)
	private BigDecimal unitPrice;
	@Column(name = "qtyofferd", precision = 10, scale = 2)
	private BigDecimal qtyOfferd;
	@Column(name = "exrate", precision = 10, scale = 2)
	private BigDecimal exRate;
	@Column(name = "basicamount", precision = 10, scale = 2)
	private BigDecimal basicAmount;
	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;
	@Column(name = "taxableamount", precision = 10, scale = 2)
	private BigDecimal taxableAmount;
	@Column(name = "taxamount", precision = 10, scale = 2)
	private BigDecimal taxAmount;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "igst", precision = 10, scale = 2)
	private BigDecimal igst;
	@Column(name = "cgst", precision = 10, scale = 2)
	private BigDecimal cgst;
	@Column(name = "sgst", precision = 10, scale = 2)
	private BigDecimal sgst;
	@Column(name = "taxcode")
	private String taxCode;
	@Column(name = "customerpono")
	private String customerPoNo;
	
	@ManyToOne
	@JoinColumn(name = "salesid")
	@JsonBackReference
	private SalesVO salesVO;

}
