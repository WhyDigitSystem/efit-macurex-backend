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
@Table(name = "qualitydocumentchangerecord")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityDocumentChangeRecordVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qualitydocumentchangerecordgen")
	@SequenceGenerator(name = "qualitydocumentchangerecordgen", sequenceName = "qualitydocumentchangerecordseq", initialValue = 1000000001, allocationSize = 1)

	@Column(name = "qualitydocumentchangerecordid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "name")
	private String name;
	@Column(name = "designation")
	private String designation;
	@Column(name = "documentdescription")
	private String documentDescription;
	@Column(name = "documentrefno")
	private String documentRefNo;
	@Column(name = "currentrevisionstatus")
	private String currentRevisionStatus;
	@Column(name = "recorddate")
	private LocalDate recordDate;
	@Column(name = "detailsofchangerequired")
	private String detailsOfChangeRequired;
	@Column(name = "reasonforchange")
	private String reasonForChange;
	@Column(name = "changes")
	private String changes;
	@Column(name = "approvedby")
	private String approvedBy;
	@Column(name = "newdocumentreleasedate")
	private LocalDate newDocumentReleaseDate;
	@Column(name = "documentformateno")
	private String documentFormateNo;
	@Column(name = "signature")
	private String signature;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@Column(name = "screencode", length = 5)
	private String screenCode = "QDCR";

	@Column(name = "screenname", length = 50)
	private String screenName = "QUALITYDOCUMENTCHANGERECORD";

	@OneToMany(mappedBy = "qualityDocumentChangeRecordVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DocumentChangeRecordAttachmentVO> documents;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}