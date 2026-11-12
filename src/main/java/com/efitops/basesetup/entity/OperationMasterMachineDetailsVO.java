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
@Table(name = "operation_master_machine_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterMachineDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_master_machine_detailsgen")
	@SequenceGenerator(name = "operation_master_machine_detailsgen", sequenceName = "operation_master_machine_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "operation_master_machine_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "machine")
	private MachineMasterVO machine;
	
	@ManyToOne
	@JoinColumn(name = "operation_master_basic_id")
	@JsonBackReference
	private OperationMasterVO operationMasterVO;
	
	
	
	

}
