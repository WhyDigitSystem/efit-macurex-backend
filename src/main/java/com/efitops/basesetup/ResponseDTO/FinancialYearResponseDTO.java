package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearResponseDTO {

    private Long id;
    private Integer finYear;
    private LocalDate startDate;
    private LocalDate endDate;

}