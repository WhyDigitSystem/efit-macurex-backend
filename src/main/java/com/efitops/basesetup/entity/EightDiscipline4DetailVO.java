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
@Table(name = "eight_discipline_4_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline4DetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_4_detailgen")
	@SequenceGenerator(name = "eight_discipline_4_detailgen", sequenceName = "eight_discipline_4_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_4_detail_id")
	private Long id;
	
	
	 @Column(name = "why_1")
	    private String why1;

	    @Column(name = "why_2")
	    private String why2;

	    @Column(name = "why_3")
	    private String why3;

	    @Column(name = "why_4")
	    private String why4;

	    @Column(name = "how")
	    private String how;

	    @Column(name = "corrective_action")
	    private String correctiveAction;

	    @Column(name = "preventive_action")
	    private String preventiveAction;

	    @Column(name = "root_cause_for_non_conformity")
	    private String rootCauseForNonConformity;

	    @ManyToOne
	    @JoinColumn(name = "eight_discipline_entry_basic_id")
	    @JsonBackReference
	    private EightDisciplineEntryVO eightDisciplineEntryVO;
	

}
