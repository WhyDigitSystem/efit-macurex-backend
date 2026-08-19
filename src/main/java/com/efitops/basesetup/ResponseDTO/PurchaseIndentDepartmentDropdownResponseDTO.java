package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIndentDepartmentDropdownResponseDTO {

    private Long id;
    private String departmentCode;
    private String departmentName;

}