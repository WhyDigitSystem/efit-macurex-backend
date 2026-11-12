package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuesDetailsResponseDTO {

    private Long id;

    private ItemResponse1DTO item;

    private BigDecimal qtyAvailable;

    private BigDecimal indentQty;

    private BigDecimal previouslyIssuedQty;

    private BigDecimal pendingQty;

    private BigDecimal qty;

    private BigDecimal rate;

    private BigDecimal amount;
}