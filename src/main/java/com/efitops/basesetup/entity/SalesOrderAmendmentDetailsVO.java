package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "sales_order_amendment_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesOrderAmendmentDetailsVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_order_amendment_detail_seq")
	@SequenceGenerator(name = "sales_order_amendment_detail_seq",sequenceName = "sales_order_amendment_detail_seq", initialValue = 1000000001,allocationSize = 1)
	@Column(name = "sales_order_amendment_detail_id")
	private Long id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item")
	private ItemMasterVO item;

//	@Column(name = "item_description")
//	private String itemDescription;

	@Column(name = "old_qty")
	private double oldQty;

	@Column(name = "old_rate")
	private double oldRate;

	@Column(name = "new_qty")
	private double newQty;

	@Column(name = "new_rate")
	private double newRate;

	@Column(name = "old_delivery_date")
	private LocalDate oldDeliveryDate;

	@Column(name = "new_delivery_date")
	private LocalDate newDeliveryDate;
	

	@ManyToOne
	@JoinColumn(name = "sales_order_amendment_id")
	@JsonBackReference
	private SalesOrderAmendmentVO salesOrderAmendmentVO;
}