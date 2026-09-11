package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftBreakTimingDetailsDTO {

	private Long id;
	private String breakCategory;
	private String breakTimings;

}
