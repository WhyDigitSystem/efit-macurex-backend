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
@Table(name = "initial_planning_instruments_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningInstrumentDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_planning_instruments_detailsgen")
	@SequenceGenerator(name = "initial_planning_instruments_detailsgen", sequenceName = "initial_planning_instruments_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_planning_instruments_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "instrument_no")
	private MachineMasterVO instrumentNo;
	
	@Column(name = "range")
	private String  range;
	
	@ManyToOne
	@JoinColumn(name = "initial_planning_details_id")
	@JsonBackReference
	private InitialPlanningDetailsVO initialPlanningDetailsVO;
	

}
