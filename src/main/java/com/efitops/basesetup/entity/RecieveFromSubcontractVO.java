package com.efitops.basesetup.entity;

import java.sql.Date;
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
@Table(name = "recievefromsubcontract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveFromSubcontractVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recievefromsubcontractgen")
	@SequenceGenerator(name = "recievefromsubcontractgen", sequenceName = "recievefromsubcontractseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "recievefromsubcontractid")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate= LocalDate.now();
	@Column(name="routecardno")
	private String routeCardNo;
	@Column(name="issueno")
	private String issueNo;
	@Column(name="issuedate")
	private Date issueDate;
	@Column(name="jobworkoutorder")
	private String jobWorkOutOrder;
	@Column(name="dcno")
	private String dcNo;
	@Column(name="department")
	private String department; 
	@Column(name="contractorname")
	private String contractorName;
	@Column(name="contractorid")
	private String contractorId;
	@Column(name="invoiceno")
	private String invoiceNo;
	@Column(name="testcertificate")
	private String testCertificate;
	
	
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
	private String screenCode ="RFSC";
	@Column(name = "screenname",length = 30)
	private String screenName="RECIEVEDFROMSUBCONTRACT";
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
	@Column(name = "finyear", length = 5)
	private String finYear;
	
	@OneToMany(mappedBy = "recieveFromSubcontractVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<RecieveFromSubContractDetailsVO> recieveFromSubContractDetailsVO;
	
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
