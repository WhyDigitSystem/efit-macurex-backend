package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferOrderDetailDTO {
	
	
	    private Long id;

	    private LocalDate orderDate;

	    private Long itemCode;

	    private String itemDescription;

	    private LocalDate scheduleDate;

	    private Double qty;

	    private String unit;

	    private Double purQty;

	    private String purUnit;

	    private Long supplierId;

	    private String supplierName;

	    private String type;

	    private String combineWith;

	    private String transId;

	    private String contractNo;

}
