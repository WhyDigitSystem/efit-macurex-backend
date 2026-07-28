package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ItemMasterResponseDTO {
	private Long id;

	private Long listOfValuesId;

	private Long primaryUnitId;

	private String capitalOrInput;

	private String itemType;

	private Long branchId;

	private BranchResponseDTO branch;

	private PrimaryUnitImageDTO primaryUnits;

	private HsnResponseImageDTO itemHsn;

	private String itemGroupType;

	private String grade;

	private String itemCode;

	private String exciseTariffNo;

	private String itemDescription;

	private BigDecimal thickness;

	private String isStock;

	private BigDecimal width;

	private String prototype;

	private BigDecimal length;

	private String psw;

	private BigDecimal weight;

	private String needQcApproval;

	private String inspection;

	private String abcGrade;

	private String drawingNo;

	private String isExciseItem;

	private BigDecimal lotSize;

	private String isShelfLifePart;

	private String importOrLocal;

	private String saftyStockMsl;

	private String isGrnRequired;

	private String rawMaterialsMake;

	private boolean active;

	private String hsnCode;

	// Purchase Item

	private PrimaryUnitImageDTO purchaseUnit;

	private PrimaryUnitImageDTO sellingUnit;

	private PrimaryUnitImageDTO pricingUnit;

	private PrimaryUnitImageDTO secondaryUnit;

	private BigDecimal secondaryPurchaseUnit;

	// Inventory

	private String manufacturedOrBoughtout;

	private String defaultLocation;

	private String alternativeLocation;

	private BigDecimal leadTime;

	private String reorderLevel;

	private String rackNo;

	private String rowNo;

	private String position;

	private String minOrderQty;

	private String maxOrderQty;

	private BigDecimal binSize;

	private BigDecimal binQty;

	private BigDecimal minimumOrderQty;

	private BigDecimal maximumOrderQty;

	// Purchase

	private String defaultSupplier;

	private String alternativeSupplier;

	private String pruchaseTalerance;

	private BigDecimal rate;

	private LocalDate date;

	private String landedCostRate;

	private String toolOwner;

	private String toolNo;

	// Sales

	private BigDecimal costRate;

	private String isItemBlockedForInvoicing;

	private BigDecimal minSellPrice;

	private BigDecimal saleAmt;

	private String leadTimeToDispatch;

	private String customerPartNo;

	private String supplierPartNo;

	private String techSpec;

	private BigDecimal salesAccount;

	// Common

	private String createdBy;

	private String updatedBy;

	private boolean cancel;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long org;

	private String financialYear;

	private ListOfImageResponseDTO listOfValues;

//	private List<ItemUnitsDTO> itemUnitsDTO;
//
//	private List<ItemInventoryDTO> itemInventoryDTO;
//
//	private List<ItemPurchaseDTO> itemPurchaseDTO;
//
//	private List<ItemSalesDTO> itemSalesDTO;
//
//	private List<ItemOthersDTO> itemOthersDTO;

	private List<ItemDrawingDTO> itemDrawingDTO;
}
