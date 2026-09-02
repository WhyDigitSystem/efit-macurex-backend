package com.efitops.basesetup.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "initial_planning_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_planning_detailsgen")
	@SequenceGenerator(name = "initial_planning_detailsgen", sequenceName = "initial_planning_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_planning_details_id")
	private Long id;
	
	@Column(name = "parameter")
	private String parameter;
	
	@Column(name = "specification")
	private String specification;
	
	@ManyToOne
	@JoinColumn(name = "uom")
	private UnitMasterVO uom;
	
	@Column(name = "acc_criteria")
	private String accCriteria;
	
	@Column(name = "inspection_method")
	private String inspectionMethod;
	
	@Column(name = "no_of_instruments_used")
	private int noOfInstrumentsUsed;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "initial_planning_basic_id")
	@JsonBackReference
	private InitialPlanningVO initialPlanningVO;
	
	@OneToMany(mappedBy = "initialPlanningDetailsVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InitialPlanningInstrumentDetailsVO> initialPlanningInstrumentDetailsVO = new ArrayList<>();

	
	
	
	

}
