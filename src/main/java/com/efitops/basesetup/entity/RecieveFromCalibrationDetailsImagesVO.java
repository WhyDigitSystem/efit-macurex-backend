package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recievefromcalibrationetailsimages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveFromCalibrationDetailsImagesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recievefromcalibrationetailsimagesgen")
	@SequenceGenerator(name = "recievefromcalibrationetailsimagesgen", sequenceName = "recievefromcalibrationetailsimagesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "recievefromcalibrationetailsimagesid")
	private Long id;

	@Column(name = "filepath")
	private String filePath;

	@Column(name = "filename")
	private String fileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toolrecievefromcalibrationdetailsid", nullable = false)
	@JsonBackReference
	private ToolRecieveFromCalibrationDetailsVO toolRecieveFromCalibrationDetailsVO;

}
