
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
@Table(name = "control_plan_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanDetailVO {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_plan_detailgen")
	    @SequenceGenerator(name = "control_plan_detailgen", sequenceName = "control_plan_detailseq", initialValue = 1000000001, allocationSize = 1)
	    @Column(name = "control_plan_detail_id")
	    private Long id;
	    
	    @Column(name = "operation_no")
	    private String operationNo;
	    
	    @ManyToOne
	    @JoinColumn(name = "machine_device")
	    private MachineMasterVO machineDevice;
	    
	    @Column(name = "process")
	    private String process;

	    @Column(name = "specification")
	    private String specification;
	    
	    @Column(name = "risk_class_special_character")
	    private String riskClassSpecialCharacter;

	    @Column(name = "evaluation_technique")
	    private String evaluationTechnique;

	    @ManyToOne
	    @JoinColumn(name = "control_method")
	    private ListOfValuesDetailsVO controlMethod;
	    
	    @Column(name = "reaction_plan")
	    private String reactionPlan;
	    
	    @Column(name = "record")
	    private String record;
	    
	    @ManyToOne
	    @JoinColumn(name = "control_plan_basic_id")
	    @JsonBackReference
	    private ControlPlanVO controlPlanVO;
	    



}
