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
@Table(name = "control_plan_michinefixture")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanMachineFixtureVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_plan_michinefixturegen")
	@SequenceGenerator(name = "control_plan_michinefixturegen", sequenceName = "control_plan_michinefixtureseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "control_plan_michinefixture_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "machine_fixture")
	 private MachineMasterVO machineFixture;
	 
	 @Column(name = "machine_fixture_name")
	 private String machineFixtureName;
	 
	 @ManyToOne
	 @JoinColumn(name = "control_plan_basic_id")
	 @JsonBackReference
	 private ControlPlanVO controlPlanVO;

}
