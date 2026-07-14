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
@Table(name = "routecardentrydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routecardentrydetailsgen")
	@SequenceGenerator(name = "routecardentrydetailsgen", sequenceName = "routecardentrydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routecardentrydetailsid")
	private Long id;
	
	@Column(name = "operationdesc")
	private String operationDesc;	
	@Column(name = "machinecenter")
	private String machineCenter;
	@Column(name = "processstart")
	private LocalDate processStart;	
	@Column(name = "processend")
	private LocalDate processEnd;
	@Column(name = "acceptedqty")
	private int acceptedQty;	
	@Column(name = "qtyrework")
	private int qtyRework;
	@Column(name = "reject")
	private String reject;
	@Column(name = "optr")
	private String optr;
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "routecardentryid")
	RouteCardEntryVO routeCardEntryVO;
}
