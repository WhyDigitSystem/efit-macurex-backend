package com.efitops.basesetup.dto;

import java.util.List;

import com.efitops.basesetup.entity.ItemVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

	private Long id;
	private String itemName;
	private String itemType;
	private String materialType;
	private String materialGroup;
	private String materialSubGroup;
	private String itemDesc;
	private String primaryUnit;
	private String hsnCode;
	private String needQcApproval;
	private String inspection;
	private String instrumentSeqCode;
	private String createdBy;
	private Long orgId;
	private boolean active ;

	
	List<ItemInventoryDTO> itemInventoryDTO;
	List<ItemTaxSlabDTO> itemTaxSlabDTO;
	List<ItemPriceSlabDTO>itemPriceSlabDTO;
}
