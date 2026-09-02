package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "tool_master_machine_history_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterMachineHistoryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_machine_history_detailsgen")
	@SequenceGenerator(name = "tool_master_machine_history_detailsgen", sequenceName = "tool_master_machine_history_detailsseq", initialValue = 1000000002, allocationSize = 1)
	@Column(name = "tool_master_machine_history_details_id")
	private Long id;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "changed_date")
	private LocalDate changedDate;
	
	@Column(name = "cost")
	private BigDecimal cost;
	
	@Column(name = "purpose")
	private String purpose;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "tool_master_basic_id")
	@JsonBackReference
	private ToolMasterVO toolMasterVO;

	

}
