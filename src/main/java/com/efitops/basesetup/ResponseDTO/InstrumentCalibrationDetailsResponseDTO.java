package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationDetailsResponseDTO {
	private LocalDate dateOfCalibration;

	private ListOfValuesDetailsResponseDTO frequency;

	private LocalDate nextScheduleDate;

}
