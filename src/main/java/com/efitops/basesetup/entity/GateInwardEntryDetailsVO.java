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
@Table(name = "gateinwardentrydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateInwardEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gateinwardentrydetailsgen")
	@SequenceGenerator(name = "gateinwardentrydetailsgen", sequenceName = "gateinwardentrydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gateinwardentrydetailsid")
	private Long id;
	
	@Column(name = "itemname")
	private String itemName;

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "uom")
	private String uom;

	@Column(name = "poqty")
	private BigDecimal poQty;
	
	@Column(name = "pobalanceqty",precision = 10, scale = 2)
	private BigDecimal poBalanceQty;

	@Column(name = "invoiceqty")
	private BigDecimal invoiceQty;

	@Column(name = "inwardqty")
	private BigDecimal inwardQty;

	@Column(name = "balanceqty")
	private int balanceQty;
	
	@Column(name = "excessqty")
	private int excessQty;
	
	@ManyToOne
	@JoinColumn(name = "gateinwardentryid")
	@JsonBackReference
	private GateInwardEntryVO gateInwardEntryVO;
}
