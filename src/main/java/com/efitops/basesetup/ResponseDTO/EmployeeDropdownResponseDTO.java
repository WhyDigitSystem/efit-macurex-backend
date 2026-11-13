package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDropdownResponseDTO {

    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String email;

}