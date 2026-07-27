package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ItemMasterResponseDTO {
	private Long id;

	private Long listOfValuesId;

	private String capital;

	private String itemType;

	private String group;

	private String grade;

	private String itemCode;

	private String exciseTariffNo;

	private String itemDescription;
	private BigDecimal thickness;

	private String stock;

	private BigDecimal width;

	private String protoType;

	private BigDecimal lenth;

	private String psw;

	private ListOfImageResponseDTO listOfValues;
	
	private PrimaryUnitImageDTO primaryUnits;

	private BigDecimal weight;

	private String needQcApproval;

	private String primaryUnit;

	private String inspection;

	private String abcGrade;

	private String drawingNo;

	private String excisbleItem;

	private BigDecimal lotSize;

	private String shelfLifePart;

	private String importLocal;

	private String safetyStock;

	private String grnRequired;

	private String rowmaterials;

	private String hsnSacCode;

	private String createdBy;

	private Long orgId;

	private String updatedBy;

	private String cancelRemarks;

	private List<ItemUnitsDTO> itemUnitsDTO;

	private List<ItemInventoryDTO> itemInventoryDTO;

	private List<ItemPurchaseDTO> itemPurchaseDTO;

	private List<ItemSalesDTO> itemSalesDTO;

	private List<ItemOthersDTO> itemOthersDTO;

	private List<ItemDrawingDTO> itemDrawingDTO;
}
