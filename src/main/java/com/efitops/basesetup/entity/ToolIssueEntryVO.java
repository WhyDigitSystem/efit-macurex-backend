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
@Table(name = "toolissueentry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolIssueEntryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toolissueentrygen")
	@SequenceGenerator(name = "toolissueentrygen", sequenceName = "toolissueentryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "toolissueentryid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "instrumentname")
	private String instrumentName;
	@Column(name = "instrumentdesc")
	private String instrumentDesc;
	@Column(name = "seqcode")
	private String seqCode;
	@Column(name = "instrumentcode")
	private String instrumentCode;
	@Column(name = "instrumentrange")
	private String instrumentRange;
	@Column(name = "location")
	private String location;
	@Column(name = "leastcount")
	private long leastCount;
	@Column(name = "frequencyofcalib")
	private String frequencyOfCalib;
	@Column(name = "calibrateddate")
	private Date calibratedDate;
	@Column(name = "dueforcalib")
	private Date dueForCalib;
	@Column(name = "calibratedcertificateno")
	private String calibratedCertificateNo;
	@Column(name = "preparedby")
	private String preparedBy;
	@Column(name = "apporvedby")
	private String apporvedBy;
	@Column(name = "remarks")
	private String remarks;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "finyear", length = 10)
	private String finYear;
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
	@Column(name = "screencode", length = 30)
	private String screenCode = "TIE";
	@Column(name = "screenname", length = 30)
	private String screenName = "TOOLISSUEENTRY";

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
	
	@OneToMany(mappedBy = "toolIssueEntryVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	List<ToolIssueEntryAttachmentVO> documents;

}
