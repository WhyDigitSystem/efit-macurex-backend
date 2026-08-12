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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_order_short_close_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderShortCloseDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_short_close_detailgen")
	@SequenceGenerator(name = "sales_order_short_close_detailgen", sequenceName = "sales_order_short_close_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sales_order_short_close_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "order_qty", precision = 10, scale = 2)
	private BigDecimal orderQty;

	@Column(name = "supplied_qty", precision = 10, scale = 2)
	private BigDecimal suppliedQty;

	@Column(name = "pending_qty", precision = 10, scale = 2)
	private BigDecimal pendingQty;

	@Column(name = "required_qty", precision = 10, scale = 2)
	private BigDecimal requiredQty;

	@Column(name = "short_close_qty", precision = 10, scale = 2)
	private BigDecimal shortCloseQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "sales_order_short_close_basic_id")
	SalesOrderShortCloseVO salesOrderShortCloseVO;
}
