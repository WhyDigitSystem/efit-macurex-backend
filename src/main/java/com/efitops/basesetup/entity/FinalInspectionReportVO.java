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
@Table(name = "finalinspectionreport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalInspectionReportVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finalinspectionreportgen")
	@SequenceGenerator(name = "finalinspectionreportgen", sequenceName = "finalinspectionreportseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "finalinspectionreportid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecard")
	private String routeCard;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "untis")
	private String untis;
	@Column(name = "customer")
	private String customer;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "inspectiondate")
	private LocalDate inspectionDate;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "lotqty")
	private int lotQty;
	@Column(name = "sampleqty")
	private int sampleQty;
	@Column(name = "documentformatno")
	private String documentFormatNo;

	// Summary

	@Column(name = "checkedby")
	private String checkedBy;
	@Column(name = "approvedby")
	private String approvedBy;

	// Summary

	@Column(name = "naration")
	private String naration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
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
	private String screenCode = "FIR";
	@Column(name = "screenname", length = 30)
	private String screenName = "FINAL INSPECTION REPORT";

	@OneToMany(mappedBy = "finalInspectionReportVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<FirDimensionalInspectionVO> firDimensionalInspectionVO; 

	@OneToMany(mappedBy = "finalInspectionReportVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<FirAppearanceInspectionVO> firAppearanceInspectionVO;
	
	@OneToMany(mappedBy = "finalInspectionReportVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<FinalInspectionReportAttachmentVO> documents;

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
