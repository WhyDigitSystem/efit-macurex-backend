package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerShipingResponseDTO {

	 // =========================
    // CUSTOMER DETAILS
    // =========================
    private Long customerId;
    private String customerCode;
    private String customerName;

    private String gstState;
    private String gstNo;
    private boolean igstApplicable;
    private String gstType;

    // =========================
    // SHIPPING DETAILS
    // =========================
    private String shippingAddress;
    private String shippingCity;
    private String shippingPincode;
}
