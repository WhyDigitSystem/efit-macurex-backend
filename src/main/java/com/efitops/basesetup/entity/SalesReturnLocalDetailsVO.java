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
@Table(name = "salesreturnlocaldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnLocalDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesreturnlocaldetailsgen")
	@SequenceGenerator(name = "salesreturnlocaldetailsgen", sequenceName = "salesreturnlocaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesreturnlocaldetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "item")
	private String item;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "units")
	private String units;
	@Column(name = "avlstkqty")
	private int avlStkQty;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "rejectqty", precision = 10, scale = 2)
	private BigDecimal rejectQty;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "taxcode")
	private String taxCode;
	@Column(name = "basicamount", precision = 10, scale = 2)
	private BigDecimal basicAmount;
	@Column(name = "sgst", precision = 10, scale = 2)
	private BigDecimal sgst;
	@Column(name = "cgst", precision = 10, scale = 2)
	private BigDecimal cgst;
	@Column(name = "igst", precision = 10, scale = 2)
	private BigDecimal igst;
	@Column(name = "taxamount", precision = 10, scale = 2)
	private BigDecimal taxAmount;
	@Column(name = "landedvalue", precision = 10, scale = 2)
	private BigDecimal landedValue;

	@ManyToOne
	@JoinColumn(name = "salesreturnlocalid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	SalesReturnLocalVO salesReturnLocalVO;

}
