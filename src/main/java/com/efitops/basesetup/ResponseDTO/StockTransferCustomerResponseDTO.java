package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferCustomerResponseDTO {
	private Long id;
	private String customerCode;
	private String customerName;
	private String accountName;
	private GSTStateResponseDTO gstState;
	private Boolean gstApplicable;
	private String gstNo;

}
