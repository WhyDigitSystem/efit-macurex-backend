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

	private Long item;

	private String itemType;

	private Long uom;

	private BigDecimal weight;

	private BigDecimal qty;

	private String manbou;

	private String sfgBomRefNo;

	private LocalDate sfgBomRefDate;

	private String scrapItem;

	private Long scrapUnit;

	private BigDecimal scrapQty;

}
