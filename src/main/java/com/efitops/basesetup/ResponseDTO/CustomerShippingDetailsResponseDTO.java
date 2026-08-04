package com.efitops.basesetup.ResponseDTO;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerShippingDetailsResponseDTO {

    private Long id;

    private String shippingAddressType;

    private String shippingAddress;

    private CityResponseDTO shippingCity;

    private StateResponseDTO shippingState;

    private CountryResponseDTO shippingCountry;

    private String name;

    private String phoneNo;

    private String email;

    private String fax;
    
    private String shippingPincode;

}