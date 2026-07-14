package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobworkout")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobWorkOutVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobworkoutgen")
	@SequenceGenerator(name = "jobworkoutgen", sequenceName = "jobworkoutseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "jobworkoutid")
	private Long id;
	@Column(name = "jobworkorderno", length = 150)
	private String jobWorkOrderNo;
	@Column(name="jobworkorderdate")
	private LocalDate jobWorkOrderDate= LocalDate.now();
	@Column(name="dcno")
	private String dcNo;
	@Column(name="routecardno")
	private String routeCardNo;
	@Column(name="quotationno")
	private String quotationNo;
	@Column(name="pono")
	private String poNo;	
	@Column(name="contractorname")
	private String contractorName;
	@Column(name="destination")
	private String destination;
	@Column(name="contractorcode")
	private String contractorCode;
	@Column(name="dispatchedthrough")
	private String dispatchedThrough;
	@Column(name="durationofprocess")
	private String durationOfProcess;
	@Column(name="taxtype")
	private String taxType;  
	@Column(name="termsofpayment")
	private String termsOfPayment;
	@Column(name="amountinwords")
	private String amountInWords;
	@Column(name="totalamount",precision = 10,scale = 2)
	private BigDecimal totalAmount;
	@Column(name="totalgrossamt",precision = 10,scale = 2)
	private BigDecimal totalGrossAmt;
	@Column(name="totaltax",precision = 10,scale = 2)
	private BigDecimal totalTax; 
	@Column(name="narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode",length = 30)
	private String screenCode ="JWO";
	@Column(name = "screenname",length = 30)
	private String screenName="JOBWORKORDER";
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
	@Column(name = "finyear", length = 5)
	private String finYear;
	
	
	@OneToMany(mappedBy = "jobWorkOutVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<JobWorkOutDetailsVO> jobWorkOutDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}
