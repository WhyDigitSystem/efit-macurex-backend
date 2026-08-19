package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIndentConversionFactorDropdownResponseDTO {

    private Long id;
    private Double multiplicationFactor;

}