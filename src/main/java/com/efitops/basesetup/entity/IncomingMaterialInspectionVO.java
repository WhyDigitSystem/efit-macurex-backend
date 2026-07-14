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
@Table(name = "incomingmaterialinspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingMaterialInspectionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incomingmaterialinspectiongen")
	@SequenceGenerator(name = "incomingmaterialinspectiongen", sequenceName = "incomingmaterialinspectionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incomingmaterialinspectionid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "materialtype")
	private String materialType;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "dcinvno")
	private String dcInvNo;
	@Column(name = "type")
	private String type;
	@Column(name = "itemno")
	private String itemNo;
	@Column(name = "material")
	private String material;
	@Column(name = "qtyreceived")
	private int qtyReceived;
	@Column(name = "inspectedquantity")
	private int inspectedQuantity;
	@Column(name = "documentformatno")
	private String documentFormatNo;
	@Column(name = "date")
	private LocalDate date;

	// VisualInspection
	@Column(name = "testcertificate")
	private String testCertificate;
	@Column(name = "acceptedqty")
	private int acceptedQty;
	@Column(name = "rework")
	private String rework;
	@Column(name = "segregate")
	private String segregate;
	@Column(name = "concessionallyaccepted")
	private String concessionallyAccepted;
	@Column(name = "scrap")
	private int scrap;

	// Summary

	@Column(name = "inspectedby")
	private String inspectedBy;
	@Column(name = "inspecteddate")
	private LocalDate inspectedDate;
	@Column(name = "approvedby")
	private String approvedBy;
	@Column(name = "approveddate")
	private LocalDate approvedDate;
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
	private String screenCode = "IMI";
	@Column(name = "screenname", length = 30)
	private String screenName = "INCOMING MATERIAL INSPECTION";

	@OneToMany(mappedBy = "incomingMaterialInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IncomingMaterialInspectionDetailsVO> incomingMaterialInspectionDetailsVO;

	@OneToMany(mappedBy = "incomingMaterialInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IncomingMaterialInspectionAppearanceVO> incomingMaterialInspectionAppearanceVO;

	@OneToMany(mappedBy = "incomingMaterialInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<IncomingAttachmentVO> documents;

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
