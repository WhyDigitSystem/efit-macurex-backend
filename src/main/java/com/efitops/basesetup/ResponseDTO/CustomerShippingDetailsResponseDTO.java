package com.efitops.basesetup.ResponseDTO;

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

    private String shippingPincode;

}