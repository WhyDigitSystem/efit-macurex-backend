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
	private String supplierName;
	private String supplierCode;
	private String address;
//	private String supplierRefNo;
//	private LocalDate supplierRefDate;
	private String gstNo;
	private String gstApproval;
	private String gstSate;

}
