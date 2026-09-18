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
@Table(name = "stock_transfer_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_transfer_detailsgen")
	@SequenceGenerator(name = "stock_transfer_detailsgen", sequenceName = "stock_transfer_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "available_qty", precision = 10, scale = 2)
	private BigDecimal availableQty;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stock_transfer_basic_id")
	private StockTransferVO stockTransferVO;
}