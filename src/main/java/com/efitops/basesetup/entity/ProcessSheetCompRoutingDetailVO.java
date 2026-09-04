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
@Table(name = "process_sheet_comp_routing_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingDetailVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_sheet_comp_routing_detailgen")
	@SequenceGenerator(name = "process_sheet_comp_routing_detailgen", sequenceName = "process_sheet_comp_routing_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "process_sheet_comp_routing_detail_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "location")
	 private LocationVO location;

	 @ManyToOne
	 @JoinColumn(name = "operation")
	 private OperationMasterVO operation;
	 
	  @Column(name = "description")
	  private String description;
	  
	  @ManyToOne
	  @JoinColumn(name = "output_item_code")
	  private ItemMasterVO outputItemCode;
	  
	  @Column(name = "spec")
	  private String spec;

	  @Column(name = "no_of_tools_fixture")
	  private Long noOfToolsFixture;
	  
	  @Column(name = "sequence")
	  private Long sequence;
	  
	  @Column(name = "activity_consum_cost")
	  private BigDecimal activityConsumCost;
	  
	  @Column(name = "cumulative_consum_cost")
	  private BigDecimal cumulativeConsumCost;
	  
	  @Column(name = "source_of_variation")
	  private String sourceOfVariation;
	  
	  @Column(name = "product_characteristics")
	  private String productCharacteristics;
	  
	  @Column(name = "process_characteristics")
	  private String processCharacteristics;
	  
	  @ManyToOne
	  @JoinColumn(name = "process_sheet_comp_routing_basic_id")
	  @JsonBackReference
	  private ProcessSheetCompRoutingVO processSheetCompRoutingVO;
	    
	  
	  


	

}
