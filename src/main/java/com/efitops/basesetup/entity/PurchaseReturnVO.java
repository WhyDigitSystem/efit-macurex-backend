package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "purchasereturn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasereturngen")
	@SequenceGenerator(name = "purchasereturngen", sequenceName = "purchasereturnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasereturnid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "suppliercode")
	private String supplierCode;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "purchaseinvoiceno")
	private String purchaseInvoiceNo;
	@Column(name = "purchaseinvoicedate")
	private LocalDate purchaseInvoiceDate;
	@Column(name = "grntime")
	private LocalTime grnTime = LocalTime.now();
	@Column(name = "pono")
	private String poNo;
	@Column(name = "gstNo")
	private String gstNo;
	@Column(name = "gststate")
	private int gstState;
	@Column(name = "address")
	private String address;
	@Column(name = "gatepassno")
	private String gatePassNo;
	@Column(name = "isreversechrg")
	private String isReverseChrg;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exchangerate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;
	@Column(name = "invdcno")
	private String invDcNo;
	@Column(name = "invdcdate")
	private LocalDate invDcDate;
	@Column(name = "gsttype")
	private String gstType;
	@Column(name = "tolocation")
	private String toLocation;
	@Column(name = "totalamounttax", precision = 10, scale = 2)
	private BigDecimal totalAmountTax;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "totalamount", precision = 10, scale = 2)
	private BigDecimal totalAmount;
	@Column(name = "amountinwords", length = 150)
	private String amountInWords;
	@Column(name = "remarks")
	private String remarks;
	@Column(name="status")
	private String status;
	@Column(name="popendingcancel")
	private boolean poPendingCancel;

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
	@Column(name = "screencode", length = 30)
	private String screenCode = "PRET";
	@Column(name = "screenname", length = 30)
	private String screenName = "PURCHASE RETURN";
	
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@OneToMany(mappedBy = "purchaseReturnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<PurchaseReturnItemVO> purchaseReturnItemVO;

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