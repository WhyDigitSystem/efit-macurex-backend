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
@Table(name = "deliverychallan_capital_items_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanCapitalItemsDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallan_capital_items_detailsgen")
	@SequenceGenerator(name = "deliverychallan_capital_items_detailsgen", sequenceName = "deliverychallan_capital_items_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychallan_capital_items_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "outgoing_item")
	private ItemMasterVO outgoingItem;

	@Column(name = "stock", precision = 15, scale = 5)
	private BigDecimal stock;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;

	@Column(name = "available_stock", precision = 15, scale = 5)
	private BigDecimal availableStock;

	@Column(name = "issue_qty", precision = 15, scale = 5)
	private BigDecimal issueQty;

	@Column(name = "unit_rate", precision = 15, scale = 5)
	private BigDecimal unitRate;

	@Column(name = "amount", precision = 15, scale = 5)
	private BigDecimal amount;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "deliverychallan_capital_items_id")
	@JsonBackReference
	private DeliveryChallanCapitalItemsVO deliveryChallanCapitalItemsVO;

	// Generate getters and setters
}