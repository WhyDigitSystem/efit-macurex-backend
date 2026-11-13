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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "process_changes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessChangesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_changesgen")
	@SequenceGenerator(name = "process_changesgen", sequenceName = "process_changesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "process_changes_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "process_change")
	private String processChange;

	@Column(name = "lay_out")
	private String layOut;
	
	@Column(name = "actions")
	private String actions;
	
	
	@Column(name = "estimated_cost", precision = 10, scale = 2)
	private BigDecimal estimatedCost;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;


}

