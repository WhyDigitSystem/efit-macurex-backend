package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIndentPreparedByDropdownResponseDTO {

    private Long id;
    private String employeeCode;
    private String employeeName;

}