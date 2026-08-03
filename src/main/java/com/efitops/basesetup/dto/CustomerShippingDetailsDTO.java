package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerShippingDetailsDTO {

//    private Long id;

    private String shippingAddressType;

    private String shippingAddress;

    private Long shippingCity;

    private Long shippingState;

    private Long shippingCountry;

    private String shippingPincode;

}
