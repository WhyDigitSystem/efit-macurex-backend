package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "inprocess_inspection_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inprocess_inspection_detailsgen")
    @SequenceGenerator(
            name = "inprocess_inspection_detailsgen",
            sequenceName = "inprocess_inspection_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "inprocess_inspection_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "parameter_type")
    private String parameterType;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "tol")
    private String tol;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time_1")
    private String time1;

    @Column(name = "obs1")
    private String obs1;

    @Column(name = "obs2")
    private String obs2;

    @Column(name = "obs3")
    private String obs3;

    @Column(name = "obs4")
    private String obs4;

    @Column(name = "obs5")
    private String obs5;

    @Column(name = "deviation_observed")
    private String deviationObserved;

    @Column(name = "corrective_action")
    private String correctiveAction;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "inprocess_inspection_id")
    @JsonBackReference
    private InprocessInspectionVO inprocessInspectionVO;
}
