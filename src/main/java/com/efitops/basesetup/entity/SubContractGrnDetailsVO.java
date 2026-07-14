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
@Table(name = "subcontractgrndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractGrnDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractgrndetailsgen")
	@SequenceGenerator(name = "subcontractgrndetailsgen", sequenceName = "subcontractgrndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractgrndetailsid")
	private Long id;
	@Column(name = "itemcode", length = 150)
	private String itemCode;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="taxtype")
	private String taxType;
	@Column(name="primaryunit")
	private String primaryUnit;
	@Column(name="porate",precision = 10,scale = 2)
	private BigDecimal poRate; 
	@Column(name="qty",precision = 10,scale = 2)
	private BigDecimal qty;
	@Column(name="pendingqty",precision = 10,scale = 2)
	private BigDecimal pendingQty;
	@Column(name="recievedqty",precision = 10,scale = 2)
	private BigDecimal recievedQty;
	@Column(name="acceptqty",precision = 10,scale = 2)
	private BigDecimal acceptQty;
	@Column(name="rejectqty",precision = 10,scale = 2)
	private BigDecimal rejectQty;
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
	@JoinColumn(name = "subcontractgrnid")
	@JsonBackReference
	private SubContractGrnVO subContractGrnVO;
}
