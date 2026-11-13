package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "activities_carried_out_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_carried_out_detailsgen")
	@SequenceGenerator(name = "activities_carried_out_detailsgen", sequenceName = "activities_carried_out_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "activities_carried_out_details_id")
	private Long id;

	@Column(name = "scheduled_activity")
	private String scheduledActivity;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "from_time")
	private LocalTime fromTime;
	
	@Column(name = "to_time")
	private LocalTime toTime;
	
	@Column(name = "checking_points")
	private String checkingPoints;
	
	@Column(name = "parameter")
	private String parameter;
	
	@Column(name = "activities_carried_out")
	private String activitiesCarriedOut;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "next_activity")
	private String nextActivity;
	
	@Column(name = "no_of_hrs")
	private BigDecimal NoOfHrs;
	
	@Column(name = "frequency")
	private String frequency;
	
	@Column(name = "next_schedule_date")
	private LocalDate nextScheduleDate;
	
	@ManyToOne
	@JoinColumn(name = "activities_carried_out_basic_id")
	@JsonBackReference
	private ActivitiesCarriedOutVO activitiesCarriedOutVO;
}
