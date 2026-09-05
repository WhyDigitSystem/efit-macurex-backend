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
@Table(name = "operation_master_tool_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterToolDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_master_tool_detailsgen")
	@SequenceGenerator(name = "operation_master_tool_detailsgen", sequenceName = "operation_master_tool_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "operation_master_tool_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tool_id")
	private ToolMasterVO tool;
	
	@ManyToOne
	@JoinColumn(name = "operation_master_basic_id")
	@JsonBackReference
	private OperationMasterVO operationMasterVO;
	
	

}
