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
@Table(name = "job_order_short_close_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderShortCloseDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_order_short_close_detailsgen")
	@SequenceGenerator(name = "job_order_short_close_detailsgen", sequenceName = "job_order_short_close_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "job_order_short_close_details_id")
	private Long id;

	// Item
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	// Order Qty
	@Column(name = "order_qty", precision = 15, scale = 5)
	private BigDecimal orderQty;

	// Supplied Qty
	@Column(name = "supplied_qty", precision = 15, scale = 5)
	private BigDecimal suppliedQty;

	// Pending Qty
	@Column(name = "pending_qty", precision = 15, scale = 5)
	private BigDecimal pendingQty;

	// Required Qty
	@Column(name = "required_qty", precision = 15, scale = 5)
	private BigDecimal requiredQty;

	// Short Close Qty
	@Column(name = "short_close_qty", precision = 15, scale = 5)
	private BigDecimal shortCloseQty;

	// Parent
	@ManyToOne
	@JoinColumn(name = "job_order_short_close_id")
	@JsonBackReference
	private JobOrderShortCloseVO jobOrderShortCloseVO;
}