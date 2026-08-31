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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tool_master_spare_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterSpareDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_spare_detailsgen")
	@SequenceGenerator(name = "tool_master_spare_detailsgen", sequenceName = "tool_master_spare_detailsseq", initialValue = 1000000002, allocationSize = 1)
	@Column(name = "tool_master_spare_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "spare_part_id")
	private ItemMasterVO sparePartId;
	
	@Column(name = "model_no")
	private String modelNo;
	
	@Column(name = "serial_no")
	private String serialNo;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "warranty_till_date")
	private LocalDate warrantyTillDate;
	
	@Column(name = "calibration_req")
	private String calibrationReq;
	
	@Column(name = "last_calib_date")
	private LocalDate lastCalibDate;
	
	@Column(name = "next_calib_date")
	private LocalDate nextCalibDate;
	
	@ManyToOne
	@JoinColumn(name = "tool_master_basic_id")
	@JsonBackReference
	private ToolMasterVO toolMasterVO;

}
