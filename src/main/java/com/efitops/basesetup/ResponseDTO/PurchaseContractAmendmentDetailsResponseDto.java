package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractAmendmentDetailsResponseDto {
	
	
	private Long id;

    // Item Master
    private PurchaseContractAmendmentDetailsItemResponseDto item;

    // Unit Master
    private UnitResponseDTO unit;

    // Rates
    private BigDecimal oldRate;
    private BigDecimal newRate;

    // Validity
    private LocalDate validFrom;
    private LocalDate newValidFrom;
    private LocalDate validTo;
    private LocalDate newValidTo;

}