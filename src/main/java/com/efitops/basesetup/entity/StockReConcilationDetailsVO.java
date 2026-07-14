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
@Table(name = "stockreconcilationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReConcilationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stockreconcilationdetailsgen")
	@SequenceGenerator(name = "stockreconcilationdetailsgen", sequenceName = "stockreconcilationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockreconcilationdetailsid")
	private Long id;
	@Column(name = "itemcode")
	private String itemCode;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "unit")
	private String unit;
	@Column(name = "bookstock", precision = 10, scale = 2)
	private BigDecimal bookstock;
	@Column(name = "actualqty", precision = 10, scale = 2)
	private BigDecimal actualQty;
	@Column(name = "difference", precision = 10, scale = 2)
	private BigDecimal difference;
//	@Column(name = "lcrate", precision = 10, scale = 2)
//	private BigDecimal lcRate;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stockreconcilationid")
	private StockReConcilationVO stockReConcilationVO;
}
