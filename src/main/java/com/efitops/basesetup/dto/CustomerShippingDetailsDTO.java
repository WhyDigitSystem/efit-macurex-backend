package com.efitops.basesetup.dto;

import javax.persistence.Column;

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
    
    private String name;

    private String phoneNo;

    private String email;
    
    @Column(name = "fax")
    private String fax;

    private Long shippingCity;

    private Long shippingState;

    private Long shippingCountry;

    private String shippingPincode;

}
