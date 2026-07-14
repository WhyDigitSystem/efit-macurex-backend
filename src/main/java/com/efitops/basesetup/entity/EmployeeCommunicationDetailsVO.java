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
@Table(name = "employeecommunicationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCommunicationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeecommunicationdetailsgen")
	@SequenceGenerator(name = "employeecommunicationdetailsgen", sequenceName = "employeecommunicationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeecommunicationdetailsid")
	private Long id;

	@Column(name = "address")
	private String address;
	@Column(name = "contactnumber")
	private String contactNumber;
	@Column(name = "emailid")
	private String emailId;
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	
	@OneToOne
	@JoinColumn(name = "employeemasterid")  // ★ MUST BE UNIQUE
	@JsonBackReference
//	@JsonIgnore
	private EmployeeMasterVO employeeMasterVO;


	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}

