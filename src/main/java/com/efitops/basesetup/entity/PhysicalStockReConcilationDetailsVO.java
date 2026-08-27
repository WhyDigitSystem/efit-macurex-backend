package com.efitops.basesetup.entity;

import java.beans.JavaBean;
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
@Table(name = "physical_stock_reconcilation_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalStockReConcilationDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physical_stock_reconcilation_detailsgen")
	@SequenceGenerator(name = "physical_stock_reconcilation_detailsgen", sequenceName = "physical_stock_reconcilation_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "physical_stock_reconcilation_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "book_stock")
	private BigDecimal bookStock;
	
	@Column(name = "actual_qty")
	private BigDecimal actualQty;
	
	@Column(name = "difference")
	private BigDecimal difference;
	
	@Column(name = "lc_rate")
	private BigDecimal lcRate;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "reasonCode")
	private String reasonCode;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@ManyToOne
	@JoinColumn(name = "physical_stock_reconcilation_basic_id")
	@JsonBackReference
	private PhysicalStockReConcilationVO physicalStockReConcilationVO;

}
