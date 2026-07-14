package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "thirdpartyinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyInspectionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thirdpartyinspectiongen")
	@SequenceGenerator(name = "thirdpartyinspectiongen", sequenceName = "thirdpartyinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "thirdpartyinspectionid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "workorderno")
	private String workOrderNo;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "thirdpartydetails")
	private String thirdPartyDetails;
	@Column(name = "thirdpartyaddress")
	private String thirdPartyAddress;

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
	@Column(name = "screencode", length = 30)
	private String screenCode = "TPRD";
	@Column(name = "screenname", length = 30)
	private String screenName = "THIRD PARTY INSPECTION";
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;

	@OneToMany(mappedBy = "thirdPartyInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ThirdPartyInspectionDetailsVO> thirdPartyInspectionDetailsVO;

//	@OneToMany(mappedBy = "thirdPartyInspectionVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	List<ThirdPartyAttachmentVO> thirdPartyAttachmentVO;
	
	@OneToMany(mappedBy = "thirdPartyInspectionVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ThirdPartyAttachmentVO> thirdPartyAttachmentVO = new ArrayList<>();
	
	@OneToMany(mappedBy = "thirdPartyInspectionVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	List<ThirdPartyAttachmentsVO> documents;

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
