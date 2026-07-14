package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toolrecievefromcalibrationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolRecieveFromCalibrationDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toolrecievefromcalibrationdetailsgen")
	@SequenceGenerator(name = "toolrecievefromcalibrationdetailsgen", sequenceName = "toolrecievefromcalibrationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "toolrecievefromcalibrationdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "instrumentid")
	private String instrumentId;
	@Column(name = "instrumentname")
	private String instrumentName;
	@Column(name = "instrumentdesc")
	private String instrumentDesc;
	@Column(name = "calibrateddate")
	private LocalDate calibratedDate;
	@Column(name = "duedate")
	private LocalDate dueDate;
	@Column(name = "frequencyforcalib")
	private String frequencyForCalib;
	@Column(name = "calibrationcertificate")
	private String calibrationCertificate;
	@Column(name = "status")
	private String status;
	@Column(name = "issqty", precision = 10, scale = 2)
	private BigDecimal issQty;
	@Column(name = "recievdqty", precision = 10, scale = 2)
	private BigDecimal recievdQty;
	@Column(name = "scorpqty", precision = 10, scale = 2)
	private BigDecimal scorpQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "toolrecievefromcalibrationid", columnDefinition = "BIGINT DEFAULT 0")
	private ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO;

	@OneToMany(mappedBy = "toolRecieveFromCalibrationDetailsVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<RecieveFromCalibrationDetailsImagesVO> recieveFromCalibrationDetailsImagesVO = new ArrayList<>();

}
