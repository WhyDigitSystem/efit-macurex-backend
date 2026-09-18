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
@Table(name = "eight_discipline_1_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline1DetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_1_detailgen")
	@SequenceGenerator(name = "eight_discipline_1_detailgen", sequenceName = "eight_discipline_1_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_1_detail_id")
	private Long id;
	
	
	@Column(name = "team_role")
    private String teamRole;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email_id")
    private String emailId;
    
    

    @ManyToOne
    @JoinColumn(name = "eight_discipline_entry_basic_id")
    @JsonBackReference
    private EightDisciplineEntryVO eightDisciplineEntryVO;

}
