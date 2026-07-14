package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryDetailsDTO {
	
	private String item;
    private String itemDesc;
    private String Unit;
    private BigDecimal qtyRequired;
    private String remarks;

}
