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
@Table(name = "inprocessinspectionappearance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InprocessInspectionAppearanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inprocessinspectionappearancegen")
	@SequenceGenerator(name = "inprocessinspectionappearancegen", sequenceName = "inprocessinspectionappearanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inprocessinspectionappearanceid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "specification")
	private String specification;
	@Column(name = "observation")
	private String observation;
	@Column(name = "remarks1")
	private String remarks1;

	@ManyToOne
	@JoinColumn(name = "inprocessinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	InprocessInspectionVO inprocessInspectionVO;

}
