package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractDetailsResponseDTO {

    // item code + item description come from the same ItemMaster record
    private ItemMasterResponseDetailsDTO itemCode;

    private String hsnCode;

    private String taxType;

    private String taxName;

    private String taxPercentage;

    private PrimaryUnitImageDTO unit;

    private BigDecimal rateInCurrency;

    private BigDecimal sgstRate;
    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;
    private BigDecimal cgstAmount;

    private BigDecimal igstRate;
    private BigDecimal igstAmount;

    private LocalDate validFrom;
    private LocalDate validTo;
}