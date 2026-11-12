package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesContractDropdownResponseDto {

    private Long id;

    private String customerContractNo;

    private LocalDate contractDate;

    private String customerPurchaseOrderNo;

}