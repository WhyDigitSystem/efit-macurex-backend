package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanDetailsResponseDTO {

    private Long id;
    private ItemResponse1DTO item;
    private String taxType;
    private String taxPercentage;
    private String hsnCode;

    private String stock;

    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal totalAssessableValue;

    private BigDecimal sgstRate;
    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;
    private BigDecimal cgstAmount;

    private BigDecimal igstRate;
    private BigDecimal igstAmount;
    
  
}
