package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "employeefinanceinformation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeFinanceInformationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeefinanceinformationgen")
	@SequenceGenerator(name = "employeefinanceinformationgen", sequenceName = "employeefinanceinformationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeefinanceinformationid")
	private Long id;

	@Column(name = "modeofpayment")
	private String modeOfPayment;
	@Column(name = "accountnumber")
	private String accountNumber;
	@Column(name = "ifsccode")
	private String ifscCode;
	@Column(name = "bankname")
	private String bankName;
	
	@Column(name = "bankbranchname")
	private String bankBranchName;
	@Column(name = "paybill")
	private String payBill;
	@Column(name = "date")
	private LocalDate date;
	
//
//	@ManyToOne
//	@JsonBackReference
////	@JsonIgnore
//	@JoinColumn(name = "employeemasterid")
//	EmployeeMasterVO employeeMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}



