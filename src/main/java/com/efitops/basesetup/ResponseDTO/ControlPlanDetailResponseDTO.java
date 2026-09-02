package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanDetailResponseDTO {
	
	private Long id;

    private String operationNo;

    private MachineMasterResponse1DTO machineDevice;

    private String process;

    private String specification;

    private String riskClassSpecialCharacter;

    private String evaluationTechnique;

    private ListOfValuesDetailsResponseDTO controlMethod;

    private String reactionPlan;

    private String record;

}
