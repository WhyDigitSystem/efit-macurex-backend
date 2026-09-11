package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialDetailsResponseDTO {

	private Long id;
	private Integer sNo;

	private ItemMasterDetailsResponseImportDTO itemCode;

	private String itemDescription;
	private String itemType;

	private UnitMasterResponseDTO uom;

	private BigDecimal weight;
	private BigDecimal qty;
	private String manbou;

	private BillOfMaterialResponseDTO sfgBomRefNo;

	private LocalDate sfgBomRefDate;

	private ItemMasterDetailsResponseImportDTO scrapItem;

	private UnitMasterResponseDTO scrapUnit;

	private BigDecimal scrapQty;
	private String idisp;
}