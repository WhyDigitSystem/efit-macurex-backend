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
@Table(name = "control_plan_sample")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanSampleVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_plan_samplegen")
	@SequenceGenerator(name = "control_plan_samplegen", sequenceName = "control_plan_sampleseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "control_plan_sample_id")
	private Long id;
	
	 @Column(name = "sample_frequency")
	 private String sampleFrequency;
	
	 @Column(name = "size")
	 private String size;

	 @ManyToOne
	 @JoinColumn(name = "control_plan_basic_id")
	 @JsonBackReference
	 private ControlPlanVO controlPlanVO;


}
