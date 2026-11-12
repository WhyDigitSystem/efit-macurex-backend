package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ScrapDetailsDTO {
	private Long scrap;
	private BigDecimal weight;
	private BigDecimal qty;
}
