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
@Table(name = "incomingmaterialinspectiondetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingMaterialInspectionDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incomingmaterialinspectiondetailsgen")
	@SequenceGenerator(name = "incomingmaterialinspectiondetailsgen", sequenceName = "incomingmaterialinspectiondetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incomingmaterialinspectiondetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "parameter")
	private String parameter;
	@Column(name = "specification")
	private String specification;
	@Column(name = "lsl", precision = 10, scale = 2)
	private BigDecimal lsl;
	@Column(name = "usl", precision = 10, scale = 2)
	private BigDecimal usl;
	@Column(name = "observedvalue", precision = 10, scale = 2)
	private BigDecimal observedvalue;
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
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "incomingmaterialinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	IncomingMaterialInspectionVO incomingMaterialInspectionVO;

}
