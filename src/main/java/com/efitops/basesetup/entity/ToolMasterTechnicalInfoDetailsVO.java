package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "tool_master_technical_info_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterTechnicalInfoDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_technical_info_detailsgen")
	@SequenceGenerator(name = "tool_master_technical_info_detailsgen", sequenceName = "tool_master_technical_info_detailseq", initialValue = 1000000002, allocationSize = 1)
	@Column(name = "tool_master_technical_info_details_id")
	private Long id;
	
	@Column(name = "tool_weight")
	private BigDecimal toolWeight;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;
	
	@Column(name = "tool_fixture_size")
	private String toolFixtureSize;
	
	@Column(name = "life_of_tool")
	private String lifeOfTool;
	
	@ManyToOne
	@JoinColumn(name = "life_type")
	private ListOfValuesDetailsVO lifeType;
	
	@Column(name = "recondition_freq")
	private BigDecimal reconditionFreq;
	
	@Column(name = "set_up_time_in_minutes")
	private BigDecimal setUpTimeInMinutes;
	
	@Column(name = "completed_life_cycle")
	private BigDecimal completedLifeCycle;
	
	@Column(name = "tool_made_of")
	private String toolMadeOf;
	
	@Column(name = "technical_specification")
	private String technicalSpecification;
	
	@Column(name = "no_of_strokes_completed")
	private BigDecimal noOfStokesCompleted;
	
	@Column(name = "strokes_completed_after_reconditioning")
	private BigDecimal strokesCompletedAfterReconditioning;
	
	@Column(name = "reconditioned_date")
	private LocalDate reconditionedDate;
	
	@Column(name = "tool_fixture_cost")
	private BigDecimal toolFixtureCost;
	
	@Column(name = "tool_fixture_amortized_recovered")
	private BigDecimal toolFixtureAmortizedRecovered;

	@ManyToOne
	@JoinColumn(name = "tool_master_basic_id")
	@JsonBackReference
	private ToolMasterVO toolMasterVO;

	

}
