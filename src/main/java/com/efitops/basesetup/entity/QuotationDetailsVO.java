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
@Table(name = "quotationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotationdetailsgen")
	@SequenceGenerator(name = "quotationdetailsgen", sequenceName = "quotationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quotationdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "partcode")
	private String partCode;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "revisionno")
	private String revisionNo;
	@Column(name = "unit")
	private String unit;
	@Column(name = "unitprice", precision = 10, scale = 2)
	private BigDecimal unitPrice;
	@Column(name = "qtyoffered", precision = 10, scale = 2)
	private BigDecimal qtyOffered;
	@Column(name = "basicprice", precision = 10, scale = 3)
	private BigDecimal basicPrice;
	@Column(name = "discount", precision = 10, scale = 3)
	private BigDecimal discount;
	@Column(name = "discountamount", precision = 10, scale = 3)
	private BigDecimal discountAmount;
	@Column(name = "quoteamount", precision = 10, scale = 3)
	private BigDecimal quoteAmount;
	@Column(name = "deliverydate")
	private LocalDate deliveryDate;

	@ManyToOne
	@JoinColumn(name = "quotationid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	QuotationVO quotationVO;
}
