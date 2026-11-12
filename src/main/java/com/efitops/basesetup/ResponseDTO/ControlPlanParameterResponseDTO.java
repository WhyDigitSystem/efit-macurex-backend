package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlPlanParameterResponseDTO {

    private Long id;

    private ParameterMasterResponse1DTO parameter;

    private String parameterType;

    private String tol;
}