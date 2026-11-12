package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNDetailsResponseDTO {

    private Long id;

    private ItemResponse1DTO incomingItem;

    private BigDecimal stock;

    private BigDecimal tolerance;

    private UnitMasterResponseDTO primaryUnit;

    private String jobOrderNo;

    private BigDecimal jobOrderQty;

    private BigDecimal joRate;

    private BigDecimal gatePassQty;

    private String inspectionable;

    private BigDecimal pendingQty;

    private BigDecimal receivedQty;

    private BigDecimal excessQty;

    private BigDecimal qtyInPrimaryUnit;

    private LocationMasterResponseDTO location;

    private BigDecimal acceptedQty;

    private BigDecimal accQtyInPrimaryUnit;

    private BigDecimal rejectedQty;

    private BigDecimal rejQtyInPrimaryUnit;

    private BigDecimal amount;

    private BigDecimal sgstRate;

    private BigDecimal cgstRate;

    private BigDecimal igstRate;
    
    private BigDecimal sgstAmount;

    private BigDecimal cgstAmount;

    private BigDecimal igstAmount;

    private List<SubContractingGRNConsumptionResponseDTO> consumption;
}