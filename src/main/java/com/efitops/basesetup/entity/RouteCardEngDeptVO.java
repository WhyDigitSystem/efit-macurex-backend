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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routecardengdept")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEngDeptVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routecardengdeptgen")
	@SequenceGenerator(name = "routecardengdeptgen", sequenceName = "routecardengdeptseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routecardengdeptid")
	private Long id;
	
	@Column(name = "preparedby")
	private String preparedBy;
	@Column(name = "prepareddate")
	private LocalDate preparedDate;
	@Column(name = "approvedby")
	private String approvedBy;
	@Column(name = "approveddate")
	private LocalDate approvedDate;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "routecardentryid")
	RouteCardEntryVO routeCardEntryVO;
}
