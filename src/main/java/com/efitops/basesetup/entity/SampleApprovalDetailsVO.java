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
@Table(name = "sampleapprovaldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleApprovalDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sampleapprovaldetailsgen")
	@SequenceGenerator(name = "sampleapprovaldetailsgen", sequenceName = "sampleapprovaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sampleapprovaldetailsid")
	private Long id;
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "specification")
	private String specification;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "lsl")
	private BigDecimal lsl;
	@Column(name = "usl")
	private BigDecimal usl;
	@Column(name = "simple1")
	private String simple1;
	@Column(name = "simple2")
	private String simple2;
	@Column(name = "simple3")
	private String simple3;
	@Column(name = "simple4")
	private String simple4;
	@Column(name = "simple5")
	private String simple5;
	@Column(name = "operator1")
	private String operator1;
	@Column(name = "operator2")
	private String operator2;
	@Column(name = "operator3")
	private String operator3;
	@Column(name = "operator4")
	private String operator4;
	@Column(name = "operator5")
	private String operator5;
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="sampleapprovalid")
    private SampleApprovalVO sampleApprovalVO; 
}
