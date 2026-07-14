package com.efitops.basesetup.entity;

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
@Table(name = "productionplandetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productionplandetailsgen")
	@SequenceGenerator(name = "productionplandetailsgen", sequenceName = "productionplandetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "productionplandetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "process")
	private String process;
	@Column(name = "qty")
	private int qty;
	@Column(name = "fromdate")
	private String fromDate;
	@Column(name = "todate")
	private String toDate;
	@Column(name = "machinename")
	private String machineName;
	@Column(name = "machineno")
	private String machineNo;
	@Column(name = "timetakeninsec")
	private String timeTakenInSec;
	@Column(name = "totaltimetaken")
	private String totalTimeTaken;
	@Column(name = "timetakeninhours")
	private String timeTakenInHours;
	@Column(name = "qtyperhr")
	private String qtyPerHr;
	@Column(name = "expminprod")
	private String expMinProd;
	@Column(name = "expmaxprod")
	private String expMaxProd;
	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "productionplanid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private ProductionPlanVO productionPlanVO;

}
