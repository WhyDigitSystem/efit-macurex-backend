package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quoterevision")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRevisionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quoterevisiongen")
	@SequenceGenerator(name = "quoterevisiongen", sequenceName = "quoterevisionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quoterevisionid")
	private Long id;

	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "validtill")
	private LocalDate validTill;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "kindattention")
	private String kindAttention;
	@Column(name = "contactno")
	private Long contactNo;
	@Column(name = "contactname")
	private String contactName;
	@Column(name = "oppurtunityname")
	private String oppurtunityName;
	@Column(name = "oppurtunityid")
	private String oppurtunityId;
	@Column(name = "producationmanager")
	private String producationManager;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "sourcedocid")
	private String sourceDocId;
	@Column(name = "sourcedocdate")
	private LocalDate sourceDocDate;

	@Column(name = "email")
	private String email;
	@Column(name = "mobilenumber")
	private String mobileNumber;
	@Column(name = "gstno")
	private String gstNo;
	@Column(name = "status")
	private String status;

	@Column(name = "address")
	private String address;
	@Column(name = "iterations")
	private String iterations;

	@Column(name = "count")
	private int count;

	@Column(name = "sourceid")
	private Long sourceId;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	// summary

	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;
	@Column(name = "discountamount", precision = 10, scale = 2)
	private BigDecimal discountAmount;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "narration")
	private String narration;
	@Column(name = "amountinwords", length = 150)
	private String amountInWords;
	@Column(name = "finyear", length = 5)
	private String finYear;

	@Column(name = "productname")
	private String productName;
	@Column(name = "category")
	private String category;

	@Column(name = "subcategory")
	private String subCategory;

	@Column(name = "sellingprice", precision = 10, scale = 2)
	private BigDecimal sellingPrice;
	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal Price;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "sourcescreencode", length = 10)
	private String sourceScreenCode;
	@Column(name = "sourcescreenname", length = 25)
	private String sourceScreenName;
//
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;

	private String screenCode = "QR";
	@Column(name = "screenname", length = 30)
	private String screenName = "QUOTEREVISION";

	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "suppliercode")
	private String supplierCode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
