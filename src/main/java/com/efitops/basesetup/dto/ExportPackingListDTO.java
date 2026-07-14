package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportPackingListDTO {

	private Long id;
	private String customerName;
	private String customerCode;
	private String customerAddress;
	private List<String> salesOrderNo;
	private String deliveryPlace;
	private String countryOfOrginGoods;
	private int noOfPackage;
	private String typeOfPackage;
	private String destinationCountry;
	private String status;
	private String lutNo;
	private BigDecimal totalQuantity;
	private BigDecimal totalGrossWeight;
	private String boxType;
	private String boxDimention;
	private int boxQuantity;
	private String narration;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;

	List<ExportPackingListDetailsDTO> exportPackingListDetailsDTO;
	List<ExportPackingShippingListDTO> exportPackingShippingListDTO;
    List<ExportPackingListTermsDTO> exportPackingListTermsDTO;

}
