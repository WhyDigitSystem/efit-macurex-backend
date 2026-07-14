package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "employeeloandetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLoanDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeloandetailsgen")
	@SequenceGenerator(name = "employeeloandetailsgen", sequenceName = "employeeloandetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeeloandetailsid")
	private Long id;

	@Column(name = "finyear")
	private String finYear;
	@Column(name = "openingbalance")
	private String openingBalance;
	@Column(name = "january")
	private BigDecimal january;
	@Column(name = "february")
	private BigDecimal february;
	
	@Column(name = "march")
	private BigDecimal march;
	@Column(name = "april")
	private BigDecimal april;
	@Column(name = "may")
	private BigDecimal may;
	
	@Column(name = "june")
	private BigDecimal june;
	@Column(name = "july")
	private BigDecimal july;
	
	@Column(name = "august")
	private BigDecimal august;
	@Column(name = "september")
	private BigDecimal september;
	@Column(name = "october")
	private BigDecimal october;
	
	@Column(name = "november")
	private BigDecimal november;
	@Column(name = "december")
	private BigDecimal december;
	

	@ManyToOne
	@JsonBackReference
//	@JsonIgnore
	@JoinColumn(name = "employeemasterid")
	EmployeeMasterVO employeeMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}




