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
@Table(name = "eight_discipline_2_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline2DetailVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eight_discipline_2_detailgen")
	@SequenceGenerator(name = "eight_discipline_2_detailgen", sequenceName = "eight_discipline_2_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "eight_discipline_2_detail_id")
	private Long id;
	
	
	@Column(name = "problem")
    private String problem;

    @Column(name = "date_of_complaint")
    private LocalDate dateOfComplaint;

    @Column(name = "repeat_discrepancy")
    private String repeatDiscrepancy;

    @Column(name = "prev_gd_control_no")
    private String prevGDControlNo;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "eight_discipline_entry_basic_id")
    @JsonBackReference
    private EightDisciplineEntryVO eightDisciplineEntryVO;
	

}
