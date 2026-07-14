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
@Table(name = "inprocessinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InprocessInspectionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inprocessinspectiongen")
	@SequenceGenerator(name = "inprocessinspectiongen", sequenceName = "inprocessinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inprocessinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "workorderno")
	private String workOrderNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "materialdrawingno")
	private String materialDrawingNo;
	@Column(name = "customer")
	private String customer;
	@Column(name = "lotqty")
	private int lotQty;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "receivedqty")
	private int receivedQty;
	@Column(name = "sampleqty")
	private int sampleQty;
	@Column(name = "documentformatno")
	private String documentFormatNo;

	// Summary

	@Column(name = "checkedby")
	private String checkedBy;
	@Column(name = "approvedby")
	private String approvedBy;
	@Column(name = "naration")
	private String naration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
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
	private String screenCode = "IPI";
	@Column(name = "screenname", length = 30)
	private String screenName = "INPROCESS INSPECTION";

	@OneToMany(mappedBy = "inprocessInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<InprocessInspectionDetailsVO> inprocessInspectionDetailsVO;

	@OneToMany(mappedBy = "inprocessInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<InprocessInspectionAppearanceVO> inprocessInspectionAppearanceVO;

	@OneToMany(mappedBy = "inprocessInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<InprocessInspectionAttachmentVO> documents;

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
