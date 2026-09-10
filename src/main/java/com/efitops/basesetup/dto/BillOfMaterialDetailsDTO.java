package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialDetailsDTO {

	private Long id;
	private Integer sNo;

	private Long itemCode;
	private String itemDescription;
	private String itemType;
	private Long uom;
	private BigDecimal weight;
	private BigDecimal qty;
	private String manbou;
	private Long sfgBomRefNo;
	private LocalDate sfgBomRefDate;
	private Long scrapItem;
	private Long scrapUnit;
	private BigDecimal scrapQty;
	private String idisp;
}
