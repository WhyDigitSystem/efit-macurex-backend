package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleLineResponseDTO {
    private Long id;
    private LocalDate planDate;
    private int weekNo;
    private BigDecimal scheduleQty;
}