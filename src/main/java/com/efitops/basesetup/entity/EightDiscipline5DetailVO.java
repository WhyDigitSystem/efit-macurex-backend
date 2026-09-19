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
@Table(name = "eight_discipline_5_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline5DetailVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_5_detailgen")
	@SequenceGenerator(name = "eight_discipline_5_detailgen", sequenceName = "eight_discipline_5_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_5_detail_id")
	private Long id;
	
	@Column(name = "permanent_corrective_actions")
    private String permanentCorrectiveActions;

    @Column(name = "responsible_person_name")
    private String responsiblePersonName;

    @Column(name = "date_implemented")
    private LocalDate dateImplemented;

    @ManyToOne
    @JoinColumn(name = "eight_discipline_entry_basic_id")
    @JsonBackReference
    private EightDisciplineEntryVO eightDisciplineEntryVO;
	
	
	

}
