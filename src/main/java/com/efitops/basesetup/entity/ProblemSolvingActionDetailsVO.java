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
@Table(name = "problem_solving_action_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingActionDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "problem_solving_action_detailsgen")
	@SequenceGenerator(name = "problem_solving_action_detailsgen", sequenceName = "problem_solving_action_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "problem_solving_action_details_id")
	private Long id;

	@Column(name = "action")
	private String action;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "responsible")
	private EmployeeMasterVO responsible;

	@Column(name = "impl_date")
	private LocalDate implDate;

	@ManyToOne
	@JoinColumn(name = "problem_solving_entry_basic_id")
	@JsonBackReference
	private ProblemSolvingEntryVO problemSolvingEntryVO;

}
