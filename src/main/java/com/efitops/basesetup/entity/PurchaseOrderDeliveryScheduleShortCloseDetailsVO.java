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
@Table(name = "proforma_order_delivery_schedule_shortclose_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proforma_order_delivery_schedule_shortclose_detailsgen")
	@SequenceGenerator(name = "proforma_order_delivery_schedule_shortclose_detailsgen", sequenceName = "proforma_order_delivery_schedule_shortclose_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "proforma_order_delivery_schedule_shortclose_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@ManyToOne
	@JoinColumn(name = "uni")
	private UnitMasterVO unit;

	@Column(name = "ordered_qty", precision = 10, scale = 3)
	private BigDecimal orderedQty;

	@Column(name = "supplied_qty", precision = 10, scale = 3)
	private BigDecimal suppliedQty;

	@Column(name = "pending_qty", precision = 10, scale = 3)
	private BigDecimal pendingQty;

	@Column(name = "new_required_qty", precision = 10, scale = 3)
	private BigDecimal newRequiredQty;

	@Column(name = "short_close_qty", precision = 10, scale = 3)
	private BigDecimal shortCloseQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "proforma_order_delivery_schedule_shortclose_basic_id")
	PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO;

}
