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
@Table(name = "quotationitemdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItemDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotationitemdetailsgen")
	@SequenceGenerator(name = "quotationitemdetailsgen", sequenceName = "quotationitemdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quotationitemdetails_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_code")
	private ItemMasterVO itemCode;

	@ManyToOne
	@JoinColumn(name = "item_description")
	private ItemMasterVO itemDescription;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "tax_name")
	private String taxName;

	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "qty_offered", precision = 10, scale = 2)
	private BigDecimal qtyOffered;

	@Column(name = "min_price", precision = 10, scale = 2)
	private BigDecimal minPrice;

	@Column(name = "enquiry_price", precision = 10, scale = 2)
	private BigDecimal enquiryPrice;

	@Column(name = "basic_price", precision = 10, scale = 2)
	private BigDecimal basicPrice;

	@Column(name = "discount_percentage", precision = 10, scale = 2)
	private BigDecimal discountPercentage;

	@Column(name = "discount_amount", precision = 10, scale = 2)
	private BigDecimal discountAmount;

	@Column(name = "last_rate", precision = 10, scale = 2)
	private BigDecimal lastRate;

	@Column(name = "l_rate", precision = 10, scale = 2)
	private BigDecimal lRate;

	@Column(name = "quotation_amount", precision = 10, scale = 2)
	private BigDecimal quotationAmount;

	@Column(name = "ed_percentage", precision = 10, scale = 2)
	private BigDecimal edPercentage;

	@Column(name = "ed_value", precision = 10, scale = 2)
	private BigDecimal edValue;

	@Column(name = "edu_percentage", precision = 10, scale = 2)
	private BigDecimal eduPercentage;

	@Column(name = "edu_val", precision = 10, scale = 2)
	private BigDecimal eduVal;

	@Column(name = "vat_percentage", precision = 10, scale = 2)
	private BigDecimal vatPercentage;

	@Column(name = "vat_value", precision = 10, scale = 2)
	private BigDecimal vatValue;

	@Column(name = "quot_rate_piece", precision = 10, scale = 2)
	private BigDecimal quotRatePiece;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "delivery_date")
	private LocalDate deliveryDate;

	@ManyToOne
	@JoinColumn(name = "currency_name")
	private CurrencyVO currencyName;

	@Column(name = "currency_symbol")
	private String currencySymbol;

	@Column(name = "enq_detail_id")
	private String enqDetailId;

	@Column(name = "offer_control")
	private String offerControl;

	@Column(name = "enquiry_item")
	private String enquiryItem;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "quotation_id")
	QuotationVO quotationVO;

}
