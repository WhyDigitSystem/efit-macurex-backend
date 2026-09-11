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
@Table(name = "schedule_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_detailsgen")
	@SequenceGenerator(name = "schedule_detailsgen", sequenceName = "schedule_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "schedule_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;


	@Column(name = "schedule_date")
	private LocalDate scheduleDate=LocalDate.now();

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;

	@Column(name = "remarks")
	private String qtyRequired;


	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "production_schedule_order_basic_id")
	ProductionScheduleOrderVO productionScheduleOrderVO;
}
