package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportPackingShippingListDTO {

	
    private String precarriage;
    private String plcOfRecpByPreCarr;
    private String vesselNo;
    private String portOfLoading;
    private String portOfUnloading;
    private String placeOfDelivery;
    private String containerNo;
    private String noOfPackage;
}
