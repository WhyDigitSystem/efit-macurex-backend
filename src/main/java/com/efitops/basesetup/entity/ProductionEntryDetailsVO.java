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
@Table(name = "production_entry_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_entry_detailsgen")
	@SequenceGenerator(name = "production_entry_detailsgen", sequenceName = "production_entry_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_entry_details_id")
	private Long id;


	@Column(name = "operation_no")
	private String operationNo;

	@Column(name = "machine")
	private String machine;

	@Column(name = "machine_name")
	private String machineName;

	@Column(name = "machine_hour_rate", precision = 10, scale = 2)
	private BigDecimal machineHourRate;

	@Column(name = "labour_hour_rate", precision = 10, scale = 2)
	private BigDecimal labourHourRate;

	@Column(name = "operation_name")
	private String operationName;

	@Column(name = "fr_time_hrs", precision = 10, scale = 2)
	private BigDecimal frTimeHrs;

	@Column(name = "fr_time_mins", precision = 10, scale = 2)
	private BigDecimal frTimeMins;

	@Column(name = "to_time_hrs", precision = 10, scale = 2)
	private BigDecimal toTimeHrs;

	@Column(name = "tot_time_mins", precision = 10, scale = 2)
	private BigDecimal totTimeMins;

	@Column(name = "stoppage_time_mins", precision = 10, scale = 2)
	private BigDecimal stoppageTimeMins;

	@Column(name = "productive_hrs_mins", precision = 10, scale = 2)
	private BigDecimal productiveHrsMins;

	@Column(name = "qty_produced", precision = 10, scale = 2)
	private BigDecimal qtyProduced;

	@Column(name = "qty_passed", precision = 10, scale = 2)
	private BigDecimal qtyPassed;

	@Column(name = "qty_rejected", precision = 10, scale = 2)
	private BigDecimal qtyRejected;

	@Column(name = "reason")
	private String reason;

	@Column(name = "qty_rework", precision = 10, scale = 2)
	private BigDecimal qtyRework;

	@Column(name = "no_of_tools")
	private Integer noOfTools;

	@Column(name = "qty_scrap", precision = 10, scale = 2)
	private BigDecimal qtyScrap;

	@Column(name = "operation_by")
	private String operationBy;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "std_run_time_pcs_in_sec", precision = 10, scale = 2)
	private BigDecimal stdRunTimePcsInSec;

	@Column(name = "std_labour_cost", precision = 10, scale = 2)
	private BigDecimal stdLabourCost;

	@Column(name = "std_mc_cost", precision = 10, scale = 2)
	private BigDecimal stdMcCost;

	@Column(name = "running_act_cost_labour", precision = 10, scale = 2)
	private BigDecimal runningActCostLabour;

	@Column(name = "running_act_cost_mc", precision = 10, scale = 2)
	private BigDecimal runningActCostMc;

	@Column(name = "std_tool_cost", precision = 10, scale = 2)
	private BigDecimal stdToolCost;

	@Column(name = "running_act_cost_tool", precision = 10, scale = 2)
	private BigDecimal runningActCostTool;

	@Column(name = "std_consum_cost", precision = 10, scale = 2)
	private BigDecimal stdConsumCost;

	@Column(name = "running_act_cost_consum", precision = 10, scale = 2)
	private BigDecimal runningActCostConsum;

	@ManyToOne
	@JoinColumn(name = "production_entry_basic_id")
	@JsonBackReference
	private ProductionEntryVO productionEntryVO;
}