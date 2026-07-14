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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detailssubmissiontobank")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetailsSubmissionToBankVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detailssubmissiontobankgen")
	@SequenceGenerator(name = "detailssubmissiontobankgen", sequenceName = "detailssubmissiontobankseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "detailssubmissiontobankid")
	private Long id;

	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	// Default Columns
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "createdby", length = 30)
	private String createdBy;
	@Column(name = "modifiedby", length = 30)
	private String modifiedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "DSTB";
	@Column(name = "screenname", length = 30)
	private String screenName = "DETAILS SUBMISSION TO BANK";
	@Column(name = "narration")
	private String narration;

	@OneToMany(mappedBy = "detailsSubmissionToBankVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DetailsSubmissionToBankDetailsVO> detailsSubmissionToBankDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
