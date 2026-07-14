package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardClosureDTO {

	private String qaManagerSign;
	private LocalDate qaManagerSignDate;
	private String plantManagerSign;
	private LocalDate plantManagerSignDate;
}
