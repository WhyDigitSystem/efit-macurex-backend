package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSalesDTO {

	private Long id;

	private String itemBlocked;

	private BigDecimal minimumSellingPrice;

	private BigDecimal salesAccount;

	private BigDecimal leadTimeToDespatch;

	private String customerPartNo;

}
