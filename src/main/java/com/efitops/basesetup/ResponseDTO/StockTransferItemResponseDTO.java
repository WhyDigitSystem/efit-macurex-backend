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
	private String hsn;
	private String customerPartNo;

	private double rate;
	private double cgst;
	private double sgst;
	private double igst;

	private Long unitmasterId;
	private String unit;
	private Long gstratemasterId;

}
