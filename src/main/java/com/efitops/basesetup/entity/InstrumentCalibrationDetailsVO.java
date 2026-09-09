package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrument_calibration_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instrument_calibration_detailsgen")
	@SequenceGenerator(name = "instrument_calibration_detailsgen", sequenceName = "instrument_calibration_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "instrument_calibration_details_id")
	private Long id;
	
	@Column(name = "date_of_calibration")
	private LocalDate dateOfCalibration;
	
	@ManyToOne
	@JoinColumn(name = "frequency")
	private ListOfValuesDetailsVO frequency;
	
	@Column(name = "next_schedule_date")
	private LocalDate nextScheduleDate;
	
	
	

}
