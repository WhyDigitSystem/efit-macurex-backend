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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailypatrolinspectiondetails1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatrolInspectionDetails1VO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dailypatrolinspectiondetails1gen")
	@SequenceGenerator(name = "dailypatrolinspectiondetails1gen", sequenceName = "dailypatrolinspectiondetails1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailypatrolinspectiondetails1id")
	private Long id;

	@Column(name ="characteristic")
    private String characteristic;
	@Column(name ="methodofinspection")
    private String methodOfInspection;
    private String lsl; 
    private String usl; 
    private String sample1;
    private String sample2;
    private String sample3;
    private String sample4;
    private String sample5;
    private String sample6;
    private String sample7;
    private String sample8;
    private String sample9;
    private String sample10;
    private String status;
    private String remarks;
    
    @ManyToOne
    @JoinColumn(name ="dailypatrolinspectionid")
    @JsonBackReference
    private DailyPatrolInspectionVO dailyPatrolInspectionVO;
}