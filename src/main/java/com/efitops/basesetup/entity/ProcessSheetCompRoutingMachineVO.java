package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "process_sheet_comp_routing_machine_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingMachineVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_sheet_comp_routing_machine_detailgen")
	@SequenceGenerator(name = "process_sheet_comp_routing_machine_detailgen", sequenceName = "process_sheet_comp_routing_machine_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "process_sheet_comp_routing_machine_detail_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "usage")
	 private ListOfValuesDetailsVO usage;
	 
	  @ManyToOne
	  @JoinColumn(name = "machine_no")
	  private MachineMasterVO machineNo;

	  @Column(name = "machine_name")
	  private String machineName;
	  
	  @Column(name = "setup_time_minutes")
	  private BigDecimal setupTimeMinutes;
	  
	  @Column(name = "output_per_hour")
	  private BigDecimal outputPerHour;
	  
	  @Column(name = "machine_hour_rate")
	  private BigDecimal machineHourRate;

	  @Column(name = "activity_mc_cost")
	  private BigDecimal activityMcCost;
	  
	  @Column(name = "labour_hour_minutes")
	  private BigDecimal labourHourMinutes;
	  
	  @Column(name = "labour_hour_rate")
	  private BigDecimal labourHourRate;
	  
	  @Column(name = "activity_labour_cost")
	  private BigDecimal activityLabourCost;

	  @Column(name = "total")
	  private BigDecimal total;
	  
	  @ManyToOne
	  @JoinColumn(name = "process_sheet_comp_routing_basic_id")
	  @JsonBackReference
	  private ProcessSheetCompRoutingVO processSheetCompRoutingVO;
	  
	  
	  

	

}
