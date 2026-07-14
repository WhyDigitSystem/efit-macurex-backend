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
@Table(name = "dailypatrolinspectionfinal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatrolInspectionFinalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dailypatrolinspectionfinalgen")
	@SequenceGenerator(name = "dailypatrolinspectionfinalgen", sequenceName = "dailypatrolinspectionfinalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailypatrolinspectionfinalid")
	private Long id;

	@Column(name ="inspectionby")
	private String inspectionBy;
	@Column(name ="incharge")
	private String inCharge;
	private String remarks;
	
	@ManyToOne
    @JoinColumn(name ="dailypatrolinspectionid")
    @JsonBackReference
    private DailyPatrolInspectionVO dailyPatrolInspectionVO;
	
}

