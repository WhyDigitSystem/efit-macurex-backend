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
@Table(name = "purchasereturnitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnItemVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasereturnitemgen")
	@SequenceGenerator(name = "purchasereturnitemgen", sequenceName = "purchasereturnitemseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasereturnitemid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "itemcode")
	private String itemCode;
	@Column(name = "itemname")
	private String itemName;
	@Column(name = "hsnsaccode")
	private String hsnSacCode;
	@Column(name = "taxcode")
	private String taxCode;
	@Column(name = "primaryunit")
	private String primaryUnit;
	@Column(name = "porate", precision = 10, scale = 2)
	private BigDecimal poRate;
	@Column(name = "rejectqty", precision = 10, scale = 2)
	private BigDecimal rejectQty;
	@Column(name = "unitprice", precision = 10, scale = 2)
	private BigDecimal unitPrice;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "sgst", precision = 10, scale = 2)
	private BigDecimal sgst;
	@Column(name = "cgst", precision = 10, scale = 2)
	private BigDecimal cgst;
	@Column(name = "igst", precision = 10, scale = 2)
	private BigDecimal igst;
	@Column(name = "taxvalue", precision = 10, scale = 2)
	private BigDecimal taxValue;
	@Column(name = "landedvalue", precision = 10, scale = 2)
	private BigDecimal landedValue;
	@Column(name = "orderqty", precision = 10, scale = 2)
	private BigDecimal orderQty;
	@Column(name = "challanqty", precision = 10, scale = 2)
	private BigDecimal challanQty;

	@ManyToOne
	@JoinColumn(name = "purchasereturnid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	PurchaseReturnVO purchaseReturnVO;
}
