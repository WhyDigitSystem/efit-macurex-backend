package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportBillPaymentDetails2DTO {

    private String chequeRtgsNo;
    private LocalDate chequeDate;
    private BigDecimal pendingAmount;
    private BigDecimal paidAmount;
    private BigDecimal totalPaidAmount;

    
}