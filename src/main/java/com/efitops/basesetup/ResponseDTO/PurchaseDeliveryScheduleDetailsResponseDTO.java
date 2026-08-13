package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDetailsResponseDTO {

    private Long id;
    private ItemMasterResponseDetailsDTO itemCode;
    private PrimaryUnitImageDTO primaryUnit;
    private PrimaryUnitImageDTO purchaseUnit;
    private BigDecimal demandQty;
    private BigDecimal availableStock;
    private BigDecimal qty;
    private BigDecimal tentativeQty;
    private BigDecimal tentativeQtyNextMonth;
    private BigDecimal rate;
}