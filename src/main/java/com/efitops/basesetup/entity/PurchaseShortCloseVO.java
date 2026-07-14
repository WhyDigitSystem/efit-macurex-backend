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

import org.springframework.format.annotation.DateTimeFormat;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseshortclose")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseShortCloseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosegen")
	@SequenceGenerator(name = "purchaseshortclosegen", sequenceName = "purchaseshortcloseseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseshortcloseid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "ponumber")
	private String poNumber;
	@Column(name = "podate")
	private LocalDate poDate;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "suppliercode")
	private String supplierCode;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "mobileno")
	private long mobileNo;
	@Column(name = "email")
	private String email;
	@Column(name = "city")
	private String city;
	@Column(name = "status")
	private String status;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "address")
	private String address;
	@Column(name = "purchasecloseddate")
	private LocalDate purchaseClosedDate;

	@Column(name = "approvestatus", length = 20)
	private String approveStatus = "Pending";
	@Column(name = "approveby", length = 20)
	private String approveBy;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	@Column(name = "approveon")
	private String approveOn;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "PSC";
	@Column(name = "screenname", length = 30)
	private String screenName = "PURCHASESHORTCLOSE";

	@OneToMany(mappedBy = "purchaseShortCloseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<PurchaseShortCloseDetailsVO> purchaseShortCloseDetailsVO;

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
