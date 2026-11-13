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
@Table(name = "purchase_order_local_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderLocalDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_local_detailsgen")
	@SequenceGenerator(name = "purchase_order_local_detailsgen", sequenceName = "purchase_order_local_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_order_local_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "indent_no")
	private String indentNo;

	@Column(name = "indent_date")
	private String indentDate;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "customer_part_no")
	private String customerPartNo;

	@ManyToOne
	@JoinColumn(name = "purchase_unit")
	private UnitMasterVO purchaseUnit;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@Column(name = "indent_qty", precision = 10, scale = 2)
	private BigDecimal indentQty;

	@Column(name = "po_qty_in_purchase_unit", precision = 10, scale = 2)
	private BigDecimal poQtyInPurchaseUnit;

	@Column(name = "qty_in_primary_unit", precision = 10, scale = 2)
	private BigDecimal qtyInPrimaryUnit;

	@Column(name = "rate_in_inr", precision = 10, scale = 2)
	private BigDecimal rateInInr;

	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;

	@Column(name = "discount_amount", precision = 10, scale = 2)
	private BigDecimal discountAmount;

	@Column(name = "amount_in_inr", precision = 10, scale = 2)
	private BigDecimal amountInInr;

	@Column(name = "delivery_date")
	private LocalDate deliveryDate;

	@Column(name = "tax_percentage", precision = 10, scale = 2)
	private BigDecimal taxPercentage;

	@Column(name = "hsn_code")
	private String hsnCode;

	@Column(name = "tax_type")
	private String taxType;

	@Column(name = "sgst_rate", precision = 10, scale = 2)
	private BigDecimal sgstRate;

	@Column(name = "sgst_amount", precision = 10, scale = 2)
	private BigDecimal sgstAmount;

	@Column(name = "cgst_rate", precision = 10, scale = 2)
	private BigDecimal cgstRate;

	@Column(name = "cgst_amount", precision = 10, scale = 2)
	private BigDecimal cgstAmount;

	@Column(name = "igst_rate", precision = 10, scale = 2)
	private BigDecimal igstRate;

	@Column(name = "igst_amount", precision = 10, scale = 2)
	private BigDecimal igstAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "purchase_order_basic_id")
	PurchaseOrderVO purchaseOrderVO;

}
