package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDropdownResponseDTO {

    private Long id;

    private String operationId;

    private String description;

    private List<OperationMachineDropdownResponseDTO> machines;
}