package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BomResponseDTO {

	private Long id;
	private String productType;
	
	private String productCode;
	
	private String productName;
	
	private String uom;
	
	private BigDecimal qty;
}
