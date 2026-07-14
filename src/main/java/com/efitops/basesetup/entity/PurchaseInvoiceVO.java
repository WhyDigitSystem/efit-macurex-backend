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
@Table(name = "purchaseinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseinvoicegen")
	@SequenceGenerator(name = "purchaseinvoicegen", sequenceName = "purchaseinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseinvoiceid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "location")
	private String location;
	@Column(name = "inwardno")
	private String inWardNo;
	@Column(name = "suppliercode")
	private String supplierCode;
	@Column(name = "gststate")
	private int gstState;
	@Column(name = "gstNo")
	private String gstNo;
	@Column(name = "isreversechrg")
	private String isReverseChrg;
	@Column(name = "address")
	private String address;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exchangerate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;
	@Column(name = "grncleartime")
	private LocalTime grnClearTime = LocalTime.now();
	@Column(name = "invdcno")
	private String invDcNo;
	@Column(name = "invdcdate")
	private LocalDate invDcDate;
	@Column(name = "gsttype")
	private String gstType;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "totalamounttax", precision = 10, scale = 2)
	private BigDecimal totalAmountTax;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "cnt")
	private String cnt;
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
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode", length = 30)
	private String screenCode = "PINV";
	@Column(name = "screenname", length = 30)
	private String screenName = "PURCHASE INVOICE";
	
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;

	@OneToMany(mappedBy = "purchaseInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<PurchaseInvoiceItemVO> purchaseInvoiceItemVO;

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
