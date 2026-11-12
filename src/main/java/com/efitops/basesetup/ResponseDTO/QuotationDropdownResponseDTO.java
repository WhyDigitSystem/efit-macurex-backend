package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDropdownResponseDTO {

    private Long quotationId;
    private String quotationNo;
    private LocalDate quotationDate;
    private String enquiryNo;
    private LocalDate enquiryDate;

}