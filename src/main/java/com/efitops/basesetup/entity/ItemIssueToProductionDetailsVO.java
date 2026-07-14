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
@Table(name = "itemisstoproddtls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemisstoproddtlsgen")
	@SequenceGenerator(name = "itemisstoproddtlsgen", sequenceName = "itemisstoproddtlsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemisstoproddtlsid")
	private Long id;
    
	@Column(name = "item")
	private String item;

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "unit")
	private String unit;

	@Column(name = "holdqty")
	private BigDecimal holdQty;
	
	@Column(name = "avgqty")
	private BigDecimal AvgQty;

	@Column(name = "reqqty")
	private BigDecimal reqQty;
	
	@Column(name = "issueqty")
	private BigDecimal issueQty;
	
	@Column(name = "issued")
	private BigDecimal issued;

	@Column(name = "pendingqty")
	private BigDecimal pendingQty;
	
	@Column(name = "pickqty")
	private BigDecimal pickQty;

	
	@ManyToOne
	@JoinColumn(name = "itemisstoprodid")
	@JsonBackReference
	private ItemIssueToProductionVO itemIssueToProductionVO;
}
