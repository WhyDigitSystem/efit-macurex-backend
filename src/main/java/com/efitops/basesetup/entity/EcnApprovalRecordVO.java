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
@Table(name = "ecnapprovalrecord")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EcnApprovalRecordVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ecnapprovalrecordgen")
   @SequenceGenerator(name = "ecnapprovalrecordgen", sequenceName = "ecnapprovalrecordseq", initialValue = 1000000001, allocationSize = 1)
   @Column(name="ecnapprovalrecordid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customer")
	private String customer;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "currentrevisionid")
	private String currentRevisionId;
	@Column(name = "currentrevisiondate")
	private LocalDate currentRevisionDate;
	@Column(name = "oldrev")
	private String oldRev;
	@Column(name = "detailsofrevision")
	private String detailsOfRevision;
	@Column(name = "resonforrevision")
	private String reasonForRevision;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "preparedby")
	private String preparedBy;
	@Column(name = "departmentp")
	private String departmentP;
	@Column(name = "tagedrawingsmodifiedby")
	private String stageDrawingsModifiedBy;
	@Column(name = "departments")
	private String departmentS;
	@Column(name = "checkedby")
	private String checkedBy;
	@Column(name = "departmentc")
	private String departmentC;
	@Column(name = "statusc")
	private String statusC;
	@Column(name = "verifiedby")
	private String verifiedBy;
	@Column(name = "departmentv")
	private String departmentV;
	@Column(name = "statusv")
	private String statusV;
	@Column(name = "approvedby")
	private String aprrovedBy;
	@Column(name = "departmenta")
	private String departmentA;
	@Column(name = "statusa")
	private String statusA;
	@Column(name = "documentformateno")
	private String documentFormateNo;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branch")
	private String branch;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks")
	private String cancelRemarks;

	@Column(name = "finyear")
	private String finYear;

	@Column(name = "screencode", length = 5)
	private String screenCode = "ECNAR";

	@Column(name = "screenname", length = 25)
	private String screenName = "ECNAPPROVALRECORD";
	
	@OneToMany(mappedBy = "ecnApprovalRecordVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<EcnAttachmentVO> documents;

	

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
