package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractItemDetailsDTO {


    private Long incomingItemCode;

    private Long purchaseUnit;

    private String platingType;

    private BigDecimal thickness;

    private BigDecimal rate;

    private BigDecimal sgstRate;


    private BigDecimal cgstRate;


    private BigDecimal igstRate;


    private LocalDate validFrom;

    private LocalDate validTo;

    private BigDecimal toolAmortizationRate;

}
