package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetailsResponseDTO {

    private Long id;
    private LocalDate scheduleDate;
    private BigDecimal qty;
    private String remarks;
}