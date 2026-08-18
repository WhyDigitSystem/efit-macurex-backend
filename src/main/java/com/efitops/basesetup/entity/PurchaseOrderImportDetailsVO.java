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
@Table(name = "purchase_order_import_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderImportDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_import_detailsgen")
	@SequenceGenerator(name = "purchase_order_import_detailsgen", sequenceName = "purchase_order_import_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_order_import_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "indent_no")
	private String indentNo;

	@Column(name = "indent_date")
	private String indentDate;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "hsn_code")
	private String hsnCode;

	@ManyToOne
	@JoinColumn(name = "uom")
	private UnitMasterVO uom;

	@Column(name = "indent_qty", precision = 10, scale = 2)
	private BigDecimal indentQty;

	@Column(name = "po_qty", precision = 10, scale = 2)
	private BigDecimal poQty;

	@Column(name = "fob_rate_fc", precision = 10, scale = 2)
	private BigDecimal fobRateFc;

	@Column(name = "fob_value_fc", precision = 10, scale = 2)
	private BigDecimal fobValueFc;

	@Column(name = "fob_rate_inr", precision = 10, scale = 2)
	private BigDecimal fobRateInr;

	@Column(name = "fob_value_inr", precision = 10, scale = 2)
	private BigDecimal fobValueInr;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "purchase_order_basic_id")
	PurchaseOrderVO purchaseOrderVO;
}
