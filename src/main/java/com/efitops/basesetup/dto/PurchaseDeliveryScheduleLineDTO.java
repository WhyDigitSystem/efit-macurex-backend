package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleLineDTO {

    private Long id;

    // [USER ENTER]
    private LocalDate planDate;

    // [USER ENTER] - assumed numeric; change to String if shown as "W1", "W2" etc.
    private Integer weekNo;

    // [USER ENTER]
    private BigDecimal scheduleQty;
}