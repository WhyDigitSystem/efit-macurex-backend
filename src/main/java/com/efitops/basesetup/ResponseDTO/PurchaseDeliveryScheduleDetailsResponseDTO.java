package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDetailsResponseDTO {

    private Long id;
    private ItemMasterResponseDetailsDTO item;
    private UnitMasterResponseDTO primaryUnit;
    private UnitMasterResponseDTO purchaseUnit;
    private BigDecimal demandQty;
    private BigDecimal availableStock;
    private BigDecimal qty;
    private BigDecimal tentativeQty;
    private BigDecimal tentativeQtyNextMonth;
    private BigDecimal rate;
    private List<PurchaseDeliveryScheduleLineResponseDTO> schedule;
}