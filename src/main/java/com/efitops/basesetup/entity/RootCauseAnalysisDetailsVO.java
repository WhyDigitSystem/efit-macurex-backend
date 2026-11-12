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
@Table(name = "root_cause_analysis_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RootCauseAnalysisDetailsVO {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "root_cause_analysis_detailgen")
	@SequenceGenerator(name = "root_cause_analysis_detailgen", sequenceName = "root_cause_analysis_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "root_cause_analysis_detail_id")
	private Long id;
	 
	 
	 @Column(name = "why1")
	 private String why1;

	 @Column(name = "why2")
	 private String why2;

	 @Column(name = "why3")
	 private String why3;

	 @Column(name = "why4")
	 private String why4;

	 @Column(name = "why5")
	 private String why5;

	 @Column(name = "how")
	 private String how;

	 @Column(name = "corrective_action")
	 private String correctiveAction;

	 @Column(name = "preventive_action")
	 private String preventiveAction;

	 @Column(name = "remarks")
	 private String remarks;
	 
	  @ManyToOne
	  @JoinColumn(name = "root_cause_analysis_basic_id")
	  @JsonBackReference
	  private RootCauseAnalysisVO rootCauseAnalysisVO;


}
