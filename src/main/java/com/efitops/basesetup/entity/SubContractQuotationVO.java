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
@Table(name = "subcontractquotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractQuotationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractquotationgen")
	@SequenceGenerator(name = "subcontractquotationgen", sequenceName = "subcontractquotationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractquotationid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "enquiryno")
	private String enquiryNo;
	@Column(name = "enquirydate")
	private LocalDate enquiryDate;
	@Column(name = "subcontractorname")
	private String subContractorName;
	@Column(name = "subcontractorid")
	private String subContractorId;
	@Column(name = "validtill")
	private LocalDate VaildTill;
	@Column(name = "gstin")
	private String gstIn;
	@Column(name="status")
	private String status;
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "contactno")
	private Long contactNo;
	@Column(name = "scissueno")
	private String scIssueNo;

	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "amountinwords", length = 150)
	private String amountInWords;
	@Column(name = "narration")
	private String narration;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "SCQ";
	@Column(name = "screenname", length = 30)
	private String screenName = "SUBCONTRACT QUOTATION";
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "count")
	private int count;
	@Column(name = "iterations")
	private String iterations;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@OneToMany(mappedBy = "subContractQuotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SubContractQuotationDetailsVO> subContractQuotationDetailsVO;
	
	
	@OneToMany(mappedBy = "subContractQuotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SubContractorQuotationAttachmentVO> documents;

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
