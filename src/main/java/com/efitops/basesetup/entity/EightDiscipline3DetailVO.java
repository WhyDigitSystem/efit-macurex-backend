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
@Table(name = "eight_discipline_3_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline3DetailVO {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_3_detailgen")
	@SequenceGenerator(name = "eight_discipline_3_detailgen", sequenceName = "eight_discipline_3_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_3_detail_id")
	private Long id;
	
	
	    @Column(name = "interim_containment_action")
	    private String interimContainmentAction;

	    @Column(name = "responsible_person_name")
	    private String responsiblePersonName;

	    @Column(name = "date_implemented")
	    private LocalDate dateImplemented;

	    @Column(name = "responsibility")
	    private String responsibility;

	    @ManyToOne
	    @JoinColumn(name = "eight_discipline_entry_basic_id")
	    @JsonBackReference
	    private EightDisciplineEntryVO eightDisciplineEntryVO;

}
