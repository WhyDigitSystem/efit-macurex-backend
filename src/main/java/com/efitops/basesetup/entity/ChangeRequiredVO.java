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
@Table(name = "changes_required")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequiredVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "changes_requiredgen")
	@SequenceGenerator(name = "changes_requiredgen", sequenceName = "changes_requiredseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "changes_required_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "fixtures")
	private String fixtures;

	@Column(name = "any_changes")
	private String anyChanges;
	
	@Column(name = "estimated_cost", precision = 10, scale = 2)
	private BigDecimal estimatedCost;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;


}
