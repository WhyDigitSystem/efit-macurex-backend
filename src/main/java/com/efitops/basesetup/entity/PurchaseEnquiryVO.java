package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseenquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchaseenquirygen")
	@SequenceGenerator(name = "purchaseenquirygen", sequenceName = "purchaseenquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseenquiryid")
	private Long id;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "customername")
	private String customerName;

	@Column(name = "customercode")
	private String customerCode;

	@Column(name = "workorderno")
	private String workOrderNo;

	@Column(name = "purchaseindentno")
	private String purchaseIndentNo;

	@Column(name = "customerpono")
	private String customerPoNo;

	@Column(name = "fgpartname")
	private String fgPartName;

	@Column(name = "fgpartdesc")
	private String fgPartDesc;

	@Column(name = "suppliername")
	private String supplierName;

	@Column(name = "suppliercode")
	private String supplierCode;

	@Column(name = "contactperson")
	private String contactPerson;

	@Column(name = "contactno")
	private String contactNo;

	@Column(name = "enquirytype")
	private String enquiryType;

	@Column(name = "enquiryduedate")
	private LocalDate enquiryDueDate;

	@Column(name = "expecteddeliverydate")
	private LocalDate expectedDeliveryDate;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifyby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode", length = 5)
	private String screenCode = "PE";

	@Column(name = "screenname", length = 25)
	private String screenName = "PURCHASEENQUIRY";

	@Column(name = "orgid")
	private Long orgId;

	private String summary;

	@OneToMany(mappedBy = "purchaseEnquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseEnquiryDetailsVO> purchaseEnquiryDetailsVO;

}
