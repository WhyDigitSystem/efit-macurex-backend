package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillSupplierResponseDTO {
	private Long id;
	private  String supplierName;
	private String supplierCode;
	private GSTStateResponseDTO gstState;
	private String gstNNo;
	private String eccType;

}
