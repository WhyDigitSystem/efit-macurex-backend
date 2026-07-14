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
@Table(name = "subcontractinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractinvoicegen")
	@SequenceGenerator(name = "subcontractinvoicegen", sequenceName = "subcontractinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractinvoiceid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "jobworkorderno ")
	private String jobWorkOrderNo;
	@Column(name = "dcno")
	private String dcno;
	@Column(name = "deliverynotedate")
	private String deliveryNoteDate;
	@Column(name = "dispatchedthrough")
	private String dispatchedThrough;
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "subcontractorname")
	private String subContractorName;
	@Column(name = "subcontractorcode")
	private String subContractorCode;
	@Column(name = "subcontractoraddress")
	private String subContractorAddress;

	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "totaltaxamount", precision = 10, scale = 2)
	private BigDecimal totalTaxAmount;
	@Column(name = "amountinwords", length = 150)
	private String amountInWords;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "SCINV";
	@Column(name = "screenname", length = 30)
	private String screenName = "SUBCONTRACT INVOICE";
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;

	@OneToMany(mappedBy = "subContractInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SubContractInvoiceDetailsVO> subContractInvoiceDetailsVO;

	@OneToMany(mappedBy = "subContractInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<SubContractInvoiceTermsVO> subContractInvoiceTermsVO;

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
