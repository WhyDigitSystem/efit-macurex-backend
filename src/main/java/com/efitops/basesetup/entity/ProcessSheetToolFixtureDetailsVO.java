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
@Table(name = "process_sheet_toolfixture_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetToolFixtureDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_sheet_toolfixture_detailgen")
	@SequenceGenerator(name = "process_sheet_toolfixture_detailgen", sequenceName = "process_sheet_toolfixture_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "process_sheet_toolfixture_detail_id")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "usage_type")
    private ListOfValuesDetailsVO usageType;
	
	 @ManyToOne
	 @JoinColumn(name = "tool_fixture_no")
	 private ToolMasterVO toolFixtureNo;
	 
	 @Column(name = "tool_fixture_name")
	 private String toolFixtureName;
	 
	 @Column(name = "activity_tool_fixture_cost")
	 private BigDecimal activityToolFixtureCost;
	 
	 @ManyToOne
	 @JoinColumn(name = "process_sheet_comp_routing_basic_id")
	 @JsonBackReference
	 private ProcessSheetCompRoutingVO processSheetCompRoutingVO;
	  
	 
	 

	

}
