package com.efitops.basesetup.entity;

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
@Table(name = "joborderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "joborderdetailsgen")
	@SequenceGenerator(name = "joborderdetailsgen", sequenceName = "joborderdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "joborderdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "timeinhours")
	private String timeInHours;
	@Column(name = "unit")
	private String unit;
	@Column(name = "hoursproduction")
	private int hoursProduction;
	@Column(name = "rework")
	private int rework;
	@Column(name = "reject")
	private int reject;
	@Column(name = "idealtime")
	private int idealTime;
	@Column(name = "cumulativetest")
	private int cumulativeTest;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "joborderid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private JobOrderVO jobOrderVO;
}
