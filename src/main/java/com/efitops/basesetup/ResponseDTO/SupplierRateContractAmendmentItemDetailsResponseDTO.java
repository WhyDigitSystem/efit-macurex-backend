package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentItemDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO itemCode;

    private UnitMasterResponseDTO unit;

    private BigDecimal oldRate;

    private BigDecimal newRate;
}
