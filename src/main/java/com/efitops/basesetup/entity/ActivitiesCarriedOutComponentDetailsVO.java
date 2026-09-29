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
@Table(name = "activities_carried_out_component_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutComponentDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_carried_out_component_detailsgen")
	@SequenceGenerator(name = "activities_carried_out_component_detailsgen", sequenceName = "activities_carried_out_component_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "activities_carried_out_component_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "req_qty")
	private BigDecimal reqQty;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "activities_carried_out_basic_id")
	@JsonBackReference
	private ActivitiesCarriedOutVO activitiesCarriedOutVO;


}
