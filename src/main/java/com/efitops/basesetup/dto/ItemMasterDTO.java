package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMasterDTO {
	private Long id;

//	private Long listOfValuesId;

	private Long primaryUnitId;

	private Long exciseTariffNoId;

	private Long branchId;

	private Long hsnId;

//	private String capitalOrInput;

	private Long gradeId;

	private String itemCode;

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

//	private String isExciseItem;

	private BigDecimal lotSize;

	private String isShelfLifePart;

	private String importOrLocal;

	private String saftyStockMsl;

	private String isGrnRequired;

	private String rawMaterialsMake;

	private boolean active;

	// Purchase Item

	private Long purchaseUnitId;

	private Long sellingUnitId;

	private Long pricingUnitId;

	private Long secondaryUnitId;

	private BigDecimal secondaryPurchaseUnit;

	// Inventory

	private String manufacturedOrBoughtout;

	private Long defaultLocationId;

	private Long alternativeLocationId;

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

	private Long defaultSupplierId;

	private Long alternativeSupplierId;

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

	private Long orgId;


	private Long itemTypeId;

	private Long itemGroupId;

	private Long capitalOrInputId;

	private Long inspectionId;

	private String groupItem;

	private String stock;

	private String excisbleItem;

	private String shelfLifePart;
	
	private List<ItemDrawingDTO> itemDrawingDTO;

}
