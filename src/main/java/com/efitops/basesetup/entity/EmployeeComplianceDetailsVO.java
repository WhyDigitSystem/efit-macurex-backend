package com.efitops.basesetup.entity;

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
@Table(name = "employeecompliancedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeComplianceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeecompliancedetailsgen")
	@SequenceGenerator(name = "employeecompliancedetailsgen", sequenceName = "employeecompliancedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeecompliancedetailsid")
	private Long id;

	@Column(name = "esino")
	private String esiNo;
	@Column(name = "uanno")
	private String uanNo;
	@Column(name = "pt")
	private boolean pt;
	@Column(name = "insurancenumber")
	private String insuranceNumber;
	
	@Column(name = "pfNumber")
	private String pfNumber;
	@Column(name = "pf")
	private boolean pf;
	@Column(name = "esi")
	private boolean esi;
	
	@OneToOne
	@JoinColumn(name = "employeemasterid")  // ★ MUST BE UNIQUE
	@JsonBackReference
//	@JsonIgnore
	private EmployeeMasterVO employeeMasterVO;


	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


