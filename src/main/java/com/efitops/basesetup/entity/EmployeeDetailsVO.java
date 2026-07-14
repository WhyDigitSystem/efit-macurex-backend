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
@Table(name = "employeedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeedetailsgen")
	@SequenceGenerator(name = "employeedetailsgen", sequenceName = "employeedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeedetailsid")
	private Long id;

	@Column(name = "employeetype")
	private String employeeType;
	@Column(name = "department")
	private String department;
	@Column(name = "dateofjoining")
	private LocalDate dateOfJoining;
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "country")
	private String country;
	@Column(name = "paycategory")
	private String payCategory;
	@Column(name = "minimumwagecategory")
	private String minimumWageCategory;
	@Column(name = "ptstate")
	private String ptState;
	@Column(name = "joblocation")
	private String jobLocation;
	@Column(name = "dateofleaving")
	private LocalDate dateOfLeaving;

	@OneToOne
	@JoinColumn(name = "employeemasterid")  // ★ MUST BE UNIQUE
	@JsonBackReference
//	@JsonIgnore
	private EmployeeMasterVO employeeMasterVO;


	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}

