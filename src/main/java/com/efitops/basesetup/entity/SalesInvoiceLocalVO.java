package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "salesinvoicelocal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceLocalVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesinvoicelocalgen")
	@SequenceGenerator(name = "salesinvoicelocalgen", sequenceName = "salesinvoicelocalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesinvoicelocalid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "packinglistno")
	private String packingListNo;
	@Column(name = "salesorderno")
	private String salesOrderNo;
	@Column(name = "gstno")
	private String gstNo;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exchangerate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;
	@Column(name = "location")
	private String location;
	@Column(name = "billingaddress")
	private String billingAddress;
	@Column(name = "shippingaddress")
	private String shippingAddress;
	@Column(name = "taxtype")
	private String taxType;

	// Summary

	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "totaltaxamount", precision = 10, scale = 2)
	private BigDecimal totalTaxAmount;
	@Column(name = "totaltaxableamount", precision = 10, scale = 2)
	private BigDecimal totalTaxableAmount;
	@Column(name = "totalamount", precision = 10, scale = 2)
	private BigDecimal totalAmount;
	@Column(name = "totalamountinwords", length = 150)
	private String totalAmountInWords;
	@Column(name = "remarks")
	private String remarks;

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
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "screencode", length = 30)
	private String screenCode = "SIL";
	@Column(name = "screenname", length = 30)
	private String screenName = "SALES INVOICE LOCAL";
	@Column(name = "terms")
	private String terms;
	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "salesInvoiceLocalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SalesInvoiceLocalDetailsVO> salesInvoiceLocalDetailsVO;

//	@OneToMany(mappedBy = "salesInvoiceLocalVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	List<SalesInvoiceLocalTermsVO> salesInvoiceLocalTermsVO;

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
