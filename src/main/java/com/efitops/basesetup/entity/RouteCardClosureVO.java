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
@Table(name = "routecardclosure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardClosureVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routecardclosuregen")
	@SequenceGenerator(name = "routecardclosuregen", sequenceName = "routecardclosureseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routecardclosureid")
	private Long id;
	
	@Column(name = "qamanagersign")
	private String qaManagerSign;
	@Column(name = "qamanagersigndate")
	private LocalDate qaManagerSignDate;
	@Column(name = "plantmanagersign")
	private String plantManagerSign;
	@Column(name = "plantmanagersigndate")
	private LocalDate plantManagerSignDate;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "routecardentryid")
	RouteCardEntryVO routeCardEntryVO;
}
