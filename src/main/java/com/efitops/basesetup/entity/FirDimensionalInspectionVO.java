package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "firdimensionalinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirDimensionalInspectionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firdimensionalinspectiongen")
	@SequenceGenerator(name = "firdimensionalinspectiongen", sequenceName = "firdimensionalinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "firdimensionalinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "specification")
	private String specification;
	@Column(name = "lsl", precision = 10, scale = 2)
	private BigDecimal lsl;
	@Column(name = "usl", precision = 10, scale = 2)
	private BigDecimal usl;
	@Column(name = "sample1")
	private String sample1;
	@Column(name = "sample2")
	private String sample2;
	@Column(name = "sample3")
	private String sample3;
	@Column(name = "sample4")
	private String sample4;
	@Column(name = "sample5")
	private String sample5;
	@Column(name = "sample6")
	private String sample6;
	@Column(name = "sample7")
	private String sample7;
	@Column(name = "sample8")
	private String sample8;
	@Column(name = "sample9")
	private String sample9;
	@Column(name = "sample10")
	private String sample10;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "finalinspectionreportid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	FinalInspectionReportVO finalInspectionReportVO;
}
