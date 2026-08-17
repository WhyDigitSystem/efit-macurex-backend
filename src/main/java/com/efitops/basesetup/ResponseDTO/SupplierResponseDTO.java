package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.entity.ListOfValuesDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {
	private Long id;
	private String supplierCode;
	private String supplierName;

}
