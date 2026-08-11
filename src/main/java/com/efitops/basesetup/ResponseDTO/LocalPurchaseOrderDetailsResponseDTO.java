package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderDetailsResponseDTO {

    private Long id;

    private Long indentDetailId;
    private String indentNo;
    private LocalDate indentDate;

    private ItemMasterResponseDetailsDTO itemCode;
    private String customerPartNo;
    private HsnResponseImageDTO hsnCode;
    private ListOfVlauesDetailsResponseDTO taxType;
    private BigDecimal taxPercent;

    private PrimaryUnitImageDTO purchaseUnit;
    private PrimaryUnitImageDTO primaryUnit;
    private BigDecimal conversionFactor;

    private BigDecimal indentQty;
    private BigDecimal pendingIndentQty;

    private BigDecimal poQtyInPurchaseUnit;
    private BigDecimal qtyInPrimaryUnit;

    private BigDecimal rateInInr;
    private BigDecimal discountPercent;
    private BigDecimal discountAmountInr;
    private BigDecimal amountInInr;
    private LocalDate deliveryDate;

    private BigDecimal sgstRate;
    private BigDecimal sgstAmount;
    private BigDecimal cgstRate;
    private BigDecimal cgstAmount;
    private BigDecimal igstRate;
    private BigDecimal igstAmount;
}