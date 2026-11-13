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
@Table(name = "control_plan_parameter")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanParameterVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_plan_parametergen")
	@SequenceGenerator(name = "control_plan_parametergen", sequenceName = "control_plan_parameterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "control_plan_parameter_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "parameter")
	 private ParameterMasterVO parameter;
	 
	 @Column(name = "parameter_type")
	 private String parameterType;
	 
	 @Column(name = "tol")
	 private String tol;
	 
	  @ManyToOne
	  @JoinColumn(name = "control_plan_basic_id")
	  @JsonBackReference
	  private ControlPlanVO controlPlanVO;



}
