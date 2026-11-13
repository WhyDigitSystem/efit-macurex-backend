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
@Table(name = "pm_check_list_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pm_check_list_detailsgen")
	@SequenceGenerator(name = "pm_check_list_detailsgen", sequenceName = "pm_check_list_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pm_check_list_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private ListOfValuesDetailsVO category;
	
	@ManyToOne
	@JoinColumn(name = "activity")
	private ActivityMasterVO activity;
	
	@Column(name = "checking_points")
	private String checkingPoints;
	
	@Column(name = "paramter")
	private String parameter;
	
	@Column(name = "specification")
	private String specification;
	
	@Column(name = "general_dev_obs")
	private String generalDevObs;
	
	@Column(name = "remedies_remarks")
	private String remediesRemarks;
	
	@Column(name = "no_of_hrs")
	private BigDecimal noOfHrs;
	
	@Column(name = "frequency")
	private String frequency;
	
	@ManyToOne
	@JoinColumn(name = "pm_check_list_master_basic_id")
	@JsonBackReference
	private PMCheckListMasterVO pmCheckListMasterVO;
	

}
