package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferItemResponseDTO {
	private Long id;
	private String itemCode;
	private String itemDescription;
	private String customerPartNo;
	private double receivedQty;
	private double freight;
	private double insurance;
	private Long exciseTariffNo;
	private Long HsnSacCode;
	

}
