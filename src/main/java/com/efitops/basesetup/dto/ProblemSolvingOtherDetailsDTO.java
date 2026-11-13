package com.efitops.basesetup.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingOtherDetailsDTO {

	private String permanentCorrectiveActions;

	private BigDecimal effectsPercentage;

}
