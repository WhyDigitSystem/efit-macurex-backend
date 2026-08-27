package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentDetailsResponseDTO {

    private Long id;

    // Item
    private PurchaseOrderAmendmentDtailsItemResponseDTO item;

    // Unit
    private UnitResponseDTO unit;

    // Old Quantity
    private Long oldQty;

    // New Quantity
    private Long newQty;

    // Old Rate
    private Long oldRate;

    // New Rate
    private Long newRate;

    // Old Delivery Date
    private LocalDate oldDeliveryDate;

    // New Delivery Date
    private LocalDate newDeliveryDate;

}