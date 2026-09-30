package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ScrapDetailsResponseDTO {
	private Long id;
	private ListOfValuesDetailsResponseDTO scrap;
	private BigDecimal weight;
	private BigDecimal qty;
}
