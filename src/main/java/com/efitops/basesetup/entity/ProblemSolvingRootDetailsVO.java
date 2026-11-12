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
@Table(name = "problem_solving_root_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingRootDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "problem_solving_root_detailsgen")
	@SequenceGenerator(name = "problem_solving_root_detailsgen", sequenceName = "problem_solving_root_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "problem_solving_root_details_id")
	private Long id;
	
	@Column(name = "root_cause")
	private String rootCause;
	
	@Column(name = "contribution_percentage")
	private BigDecimal contributionPercentage;
	
	@ManyToOne
	@JoinColumn(name = "problem_solving_entry_basic_id")
	@JsonBackReference
	private ProblemSolvingEntryVO problemSolvingEntryVO;

}
