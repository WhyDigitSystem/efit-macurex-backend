package com.efitops.basesetup.entity;

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
@Table(name = "inward_inspection_measurements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionMeasurementsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inward_inspection_measurementsgen")
	@SequenceGenerator(name = "inward_inspection_measurementsgen", sequenceName = "inward_inspection_measurementsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inward_inspection_measurements_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "parameters")
	private String parameters;

	@Column(name = "type")
	private String type;

	@Column(name = "spec")
	private String spec;

	@Column(name = "acc_criteria")
	private String accCriteria;

	@Column(name = "uom")
	private String uom;

	@Column(name = "test_1")
	private String test1;

	@Column(name = "test_2")
	private String test2;

	@Column(name = "test_3")
	private String test3;

	@Column(name = "test_4")
	private String test4;

	@Column(name = "test_5")
	private String test5;

	@Column(name = "status")
	private String status;

	@Column(name = "remarks")	
	private String remarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "inward_inspection_details_id")
	InwardInspectionDetailsVO inwardInspectionDetailsVO;

}
