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
@Table(name = "inspection_testing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionTestingVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_testinggen")
	@SequenceGenerator(name = "inspection_testinggen", sequenceName = "inspection_testingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inspection_testing_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "new_gauge")
	private String newGauge;
	
	
	@Column(name = "estimated_cost", precision = 10, scale = 2)
	private BigDecimal estimatedCost;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;

}
