package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCapitalItemsDetailsResponseDTO {

    private Long id;

    private ItemResponse1DTO outgoingItem;

    private BigDecimal stock;

    private UnitMasterResponseDTO unit;

    private LocationMasterResponseDTO fromLocation;

    private BigDecimal availableStock;

    private BigDecimal issueQty;

    private BigDecimal unitRate;

    private BigDecimal amount;

    private String remarks;
}