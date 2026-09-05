package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingDetailsResponseDTO {

    private Long id;

    private ItemResponseDTO outgoingItem;

    private BigDecimal stock;

    private UnitResponseDTO unit;

    private LocationMasterResponseDTO fromLocation;

    private BigDecimal availableStock;

    private BigDecimal issueQty;

    private BigDecimal unitRate;

    private BigDecimal amount;

    private String remarks;
}
