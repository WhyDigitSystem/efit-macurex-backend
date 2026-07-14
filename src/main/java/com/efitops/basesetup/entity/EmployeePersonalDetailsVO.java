package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employeepersonaldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePersonalDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeepersonaldetailsgen")
	@SequenceGenerator(name = "employeepersonaldetailsgen", sequenceName = "employeepersonaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeepersonaldetailsid")
	private Long id;

	@Column(name = "birthplace")
	private String birthPlace;
	@Column(name = "religion")
	private String religion;
	@Column(name = "passportno")
	private String passportNo;
	@Column(name = "homestate")
	private String homeState;
	
	@Column(name = "nationality")
	private String nationality;
	@Column(name = "expirydate")
	private LocalDate expiryDate;
	@Column(name = "countryoforigin")
	private String countryOfOrigin;
	@Column(name = "placeofissue")
	private String placeOfIssue;
	
	

	@OneToOne
	@JoinColumn(name = "employeemasterid")  // ★ MUST BE UNIQUE
	@JsonBackReference
//	@JsonIgnore
	private EmployeeMasterVO employeeMasterVO;


	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


