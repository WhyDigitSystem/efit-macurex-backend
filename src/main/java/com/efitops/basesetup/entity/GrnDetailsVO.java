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
@Table(name = "grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grndetailsgen")
	@SequenceGenerator(name = "grndetailsgen", sequenceName = "grndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;
	@Column(name = "itemcode", length = 150)
	private String itemCode;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="hsnsaccode")
	private String hsnSacCode;
	@Column(name="taxtype")
	private String taxType;
	@Column(name="status")
	private String status;
	@Column(name="primaryunit")
	private String primaryUnit;
	@Column(name="stock",precision = 10,scale = 2)
	private BigDecimal stock;
	@Column(name="inspectionable")
	private String inspectionable;
	@Column(name="porate",precision = 10,scale = 2)
	private BigDecimal poRate; 
	@Column(name="orderqty",precision = 10,scale = 2)
	private BigDecimal orderQty;
	@Column(name="challanqty",precision = 10,scale = 2)
	private BigDecimal challanQty;
	@Column(name="pendingqty",precision = 10,scale = 2)
	private BigDecimal pendingQty;
	@Column(name="recievedqty",precision = 10,scale = 2)
	private BigDecimal recievedQty;
	@Column(name="acceptqty",precision = 10,scale = 2)
	private BigDecimal acceptQty;
	@Column(name="rejectqty",precision = 10,scale = 2)
	private BigDecimal rejectQty;
	@Column(name="excessqty",precision = 10,scale = 2)
	private BigDecimal excessQty;
	@Column(name="amount",precision = 10,scale = 2)
	private BigDecimal amount;
	@Column(name="sgst",precision = 10,scale = 2)
	private BigDecimal sgst;
	@Column(name="cgst",precision = 10,scale = 2)
	private BigDecimal cgst;
	@Column(name="igst",precision = 10,scale = 2)
	private BigDecimal igst;
	@Column(name="taxvalue",precision = 10,scale = 2)
	private BigDecimal taxValue;
	@Column(name="landedvalue",precision = 10,scale = 2)
	private BigDecimal landedValue;
		
	@ManyToOne
	@JoinColumn(name = "grnid")
	@JsonBackReference
	private GrnVO grnVO;
}
