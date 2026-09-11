package com.efitops.basesetup.dto;

import java.time.LocalDate;

import com.efitops.basesetup.entity.ListOfValuesDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationDetailsDTO {
	
	private LocalDate dateOfCalibration;
	
	private Long frequency;
	
	private LocalDate nextScheduleDate;

}
