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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incomingmaterialinspectionappearance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingMaterialInspectionAppearanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incomingmaterialinspectionappearancegen")
	@SequenceGenerator(name = "incomingmaterialinspectionappearancegen", sequenceName = "incomingmaterialinspectionappearanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incomingmaterialinspectionappearanceid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "specifications")
	private String specifications;

	@ManyToOne
	@JoinColumn(name = "incomingmaterialinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	IncomingMaterialInspectionVO incomingMaterialInspectionVO;

}
