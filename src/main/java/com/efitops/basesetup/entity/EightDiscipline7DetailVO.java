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
@Table(name = "eight_discipline_7_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline7DetailVO {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_7_detailgen")
	@SequenceGenerator(name = "eight_discipline_7_detailgen", sequenceName = "eight_discipline_7_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_7_detail_id")
	private Long id;
	
	
	 @Column(name = "action_to_prevent_recurrence")
	    private String actionToPreventRecurrence;

	    @Column(name = "responsible_person_name")
	    private String responsiblePersonName;

	    @Column(name = "responsibility")
	    private String responsibility;

	    @ManyToOne
	    @JoinColumn(name = "eight_discipline_entry_basic_id")
	    @JsonBackReference
	    private EightDisciplineEntryVO eightDisciplineEntryVO;

	
	

}
