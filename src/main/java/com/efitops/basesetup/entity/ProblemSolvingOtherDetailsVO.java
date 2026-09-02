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
@Table(name = "problem_solving_other_details")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProblemSolvingOtherDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "problem_solving_other_detailsgen")
	@SequenceGenerator(name = "problem_solving_other_detailsgen", sequenceName = "problem_solving_other_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "problem_solving_other_details_id")
	private Long id;
	
	@Column(name = "permanent_corrective_actions")
	private String permanentCorrectiveActions;
	
	@Column(name = "effects_percentage")
	private BigDecimal effectsPercentage;
	
	@ManyToOne
	@JoinColumn(name = "problem_solving_entry_basic_id")
	@JsonBackReference
	private ProblemSolvingEntryVO problemSolvingEntryVO;

}
