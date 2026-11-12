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
@Table(name = "pre_delivery_inspection_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDeliveryInspectionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_delivery_inspection_detailsgen")
	@SequenceGenerator(name = "pre_delivery_inspection_detailsgen", sequenceName = "pre_delivery_inspection_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pre_delivery_inspection_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "parameter")
	private String parameter;

	@Column(name = "parameter_type")
	private String parameterType;

	@Column(name = "specification")
	private String specification;

	@Column(name = "instrument_name")
	private String instrumentName;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "tol")
	private String tol;

	@Column(name = "method")
	private String method;

	@Column(name = "obs_1")
	private String obs1;

	@Column(name = "obs_2")
	private String obs2;

	@Column(name = "obs_3")
	private String obs3;

	@Column(name = "obs_4")
	private String obs4;

	@Column(name = "obs_5")
	private String obs5;

	@ManyToOne
	@JoinColumn(name = "pre_delivery_inspection_basic_id")
	@JsonBackReference
	private PreDeliveryInspectionVO preDeliveryInspectionVO;
}
