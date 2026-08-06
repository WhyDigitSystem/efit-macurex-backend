package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesorderamendmentdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalesOrderAmendmentDetailsVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salesorderamendmentdetails_seq")
	@SequenceGenerator(name = "salesorderamendmentdetails_seq",sequenceName = "salesorderamendmentdetails_seq",allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sales_order_amendment_id")
	private SalesOrderAmendmentVO salesOrderAmendmentVO;

	@Column(name = "sl_no")
	private Integer slNo;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "old_qty")
	private Double oldQty;

	@Column(name = "old_rate")
	private Double oldRate;

	@Column(name = "new_qty")
	private Double newQty;

	@Column(name = "new_rate")
	private Double newRate;

	@Column(name = "old_delivery_date")
	private LocalDate oldDeliveryDate;

	@Column(name = "new_delivery_date")
	private LocalDate newDeliveryDate;
}