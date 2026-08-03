package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemgen")
	@SequenceGenerator(name = "itemgen", sequenceName = "itemseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "item_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "thickness", precision = 10, scale = 2)
	private BigDecimal thickness;


	@Column(name = "width", precision = 10, scale = 2)
	private BigDecimal width;

	@Column(name = "prototype")
	private String prototype;

	@Column(name = "length", precision = 10, scale = 2)
	private BigDecimal length;

	@Column(name = "psw")
	private String psw;

	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;

	@Column(name = "need_qc_approval")
	private String needQcApproval;

	@Column(name = "abc_grade")
	private String abcGrade;

	@Column(name = "drawing_no")
	private String drawingNo;

	@Column(name = "is_excise_item")
	private String isExciseItem;

	@Column(name = "lot_size", precision = 10, scale = 2)
	private BigDecimal lotSize;

	@Column(name = "is_shelf_life_part")
	private String isShelfLifePart;

	@Column(name = "import_or_local")
	private String importOrLocal;

	@Column(name = "safty_stock_msl")
	private String saftyStockMsl;

	@Column(name = "is_grn_required")
	private String isGrnRequired;

	@Column(name = "raw_materials_make")
	private String rawMaterialsMake;

	@Column(name = "active")
	private boolean active = true;

//	@Column(name = "hsn_code")
//	private String hsnCode;

	// Purchase Item

	@ManyToOne
	@JoinColumn(name = "purchase_unit")
	private UnitMasterVO purchaseUnit;

	@ManyToOne
	@JoinColumn(name = "selling_unit")
	private UnitMasterVO sellingUnit;

	@ManyToOne
	@JoinColumn(name = "pricing_unit")
	private UnitMasterVO pricingUnit;

	@ManyToOne
	@JoinColumn(name = "secondary_unit")
	private UnitMasterVO secondaryUnit;

	@Column(name = "secondary_purchase_unit", precision = 10, scale = 2)
	private BigDecimal secondaryPurchaseUnit;

	// inventory

	@Column(name = "manufactured_or_boughtout")
	private String manufacturedOrBoughtout;

//	@Column(name = "default_location")
//	private String defaultLocation;
//
//	@Column(name = "alternative_location")
//	private String alternativeLocation;

	@ManyToOne
	@JoinColumn(name = "default_location")
	private LocationVO defaultLocation;

	@ManyToOne
	@JoinColumn(name = "alternative_location")
	private LocationVO alternativeLocation;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@Column(name = "reorder_level")
	private String reorderLevel;

	@Column(name = "rack_no")
	private String rackNo;

	@Column(name = "row_no")
	private String rowNo;

	@Column(name = "position")
	private String position;

	@Column(name = "min_order_qty")
	private String minOrderQty;

	@Column(name = "max_order_qty")
	private String maxOrderQty;

	@Column(name = "bin_size", precision = 10, scale = 2)
	private BigDecimal binSize;

	@Column(name = "bin_qty", precision = 10, scale = 2)
	private BigDecimal binQty;

	@Column(name = "minimum_order_qty", precision = 10, scale = 2)
	private BigDecimal minimumOrderQty;

	@Column(name = "maximum_order_qty", precision = 10, scale = 2)
	private BigDecimal maximumOrderQty;

	// purchae

//	@Column(name = "default_supplier")
//	private String defaultSupplier;
//
//	@Column(name = "alternative_supplier")
//	private String alternativeSupplier;

	@ManyToOne
	@JoinColumn(name = "default_supplier")
	private CustomerVO defaultSupplier;

	@ManyToOne
	@JoinColumn(name = "alternative_supplier")
	private CustomerVO alternativeSupplier;

	@Column(name = "pruchase_talerance")
	private String pruchaseTalerance;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "landed_cost_rate")
	private String landedCostRate;

	@Column(name = "tool_owner")
	private String toolOwner;

	@Column(name = "tool_no")
	private String toolNo;

	// Sales

	@Column(name = "cost_rate", precision = 10, scale = 2)
	private BigDecimal costRate;

	@Column(name = "is_item_blocked_for_invoicing")
	private String isItemBlockedForInvoicing;

	@Column(name = "min_sell_price", precision = 10, scale = 2)
	private BigDecimal minSellPrice;

	@Column(name = "sale_amt", precision = 10, scale = 2)
	private BigDecimal saleAmt;

	@Column(name = "lead_time_to_dispatch")
	private String leadTimeToDispatch;

	@Column(name = "customer_part_no")
	private String customerPartNo;

	@Column(name = "supplier_part_no")
	private String supplierPartNo;

	@Column(name = "tech_spec")
	private String techSpec;

	@Column(name = "sales_account")
	private BigDecimal salesAccount;

	//

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "primary_unit")
	private UnitMasterVO primaryUnit;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hsn_code")
	private HsnVO hsnCode;

	@ManyToOne
	@JoinColumn(name = "itemType")
	private ListOfValuesDetailsVO itemType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade")
	private GradeMasterVO grade;

	@ManyToOne
	@JoinColumn(name = "excise_tariff_no")
	private ListOfValuesDetailsVO exciseTariffNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "capital_or_input")
	private ListOfValuesDetailsVO capitalOrInput;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "inspection")
	private ListOfValuesDetailsVO inspection;

	@ManyToOne
	@JoinColumn(name = "item_group")
	private ListOfValuesDetailsVO itemGroup;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "group_item")
	private String groupItem;

	@Column(name = "stock")
	private String stock;

	@Column(name = "excisble_item")
	private String excisbleItem;

	@Column(name = "shelf_life_part")
	private String shelfLifePart;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemDrawingVO> itemDrawingVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
