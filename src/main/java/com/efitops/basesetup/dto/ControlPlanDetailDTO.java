package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanDetailDTO {
	
	    private Long id;

	    private String operationNo;

	    private Long machineDevice;

	    private String process;

	    private String specification;

	    private String riskClassSpecialCharacter;

	    private String evaluationTechnique;

	    private Long controlMethod;

	    private String reactionPlan;

	    private String record;

}
