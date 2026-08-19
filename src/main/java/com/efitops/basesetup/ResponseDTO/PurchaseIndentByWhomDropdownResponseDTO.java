package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIndentByWhomDropdownResponseDTO {

    private Long id;
    private String employeeId;
    private String employeeName;

}