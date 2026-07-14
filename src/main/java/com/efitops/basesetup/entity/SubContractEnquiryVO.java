package com.efitops.basesetup.entity;

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
@Table(name = "subcontractenquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractEnquiryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractenquirygen")
	@SequenceGenerator(name = "subcontractenquirygen", sequenceName = "subcontractenquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractenquiryid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "enquirytype")
	private String enquiryType;
	@Column(name = "subcontractorname")
	private String subContractorName;
	@Column(name = "subcontractorrefno")
	private String subContractorRefNo;
	@Column(name = "subcontractorrefdate")
	private LocalDate subContractorRefDate;
	@Column(name = "enquiryduedate")
	private LocalDate enquiryDueDate;
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "contactname")
	private String contactName;
	@Column(name = "contactno")
	private String contactNo;
	@Column(name = "scissueno")
	private String scIssueNo;
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
	private String screenCode = "SUB";
	@Column(name = "screenname", length = 30)
	private String screenName = "SUBCONTRACT ENQUIRY";
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;


	@OneToMany(mappedBy = "subContractEnquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SubContractEnquiryDetailsVO> subContractEnquiryDetailsVO;

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
