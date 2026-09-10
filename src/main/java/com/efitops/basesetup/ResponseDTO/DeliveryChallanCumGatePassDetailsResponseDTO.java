package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCumGatePassDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO item;

    private HsnResponseDTO hsnSacCode;

    private UnitMasterResponseDTO unit;

    private BigDecimal stock;

    private BigDecimal availableQty;

    private BigDecimal qty;

    private LocalDate dueDate;

    private BigDecimal previousQty;

    private BigDecimal lcRate;

    private BigDecimal rate;

    private BigDecimal amount;
}