package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponseDTO {

    private Long id;

    private String departmentCode;

    private String departmentName;

}
