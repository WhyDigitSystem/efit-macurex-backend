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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesgen")
	@SequenceGenerator(name = "salesgen", sequenceName = "salesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exchangrate")
	private Long exChangeRate;
	@Column(name = "customerpono")
	private String customerPoNo;
	@Column(name = "workordeno")
	private String workOrderNo;
	@Column(name = "shippingaddress")
	private String shippingAddress;
	@Column(name = "billingaddress")
	private String billingAddress;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "customermail")
	private String customerMail;
	@Column(name = "placeofsupply")
	private String placeOfSupply;
	@Column(name = "taxtype")
	private String taxType;
	@Column(name = "invoicetype")
	private String invoiceType;
	@Column(name = "duedate")
	private LocalDate dueDate;
	@Column(name="narration")
	private String narration;

	@Column(name = "screencode")
	private String screenCode = "SO";
	@Column(name = "screenname")
	private String screenName = "SALESORDER";
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
	private String finYear;


	@Column(name = "totaltaxamount", precision = 10, scale = 2)
	private BigDecimal totalTaxAmount;
	@Column(name = "grossamount", precision = 10, scale = 2)
	private BigDecimal grossAmount;
	@Column(name = "netamount", precision = 10, scale = 2)
	private BigDecimal netAmount;
	@Column(name = "amountinwords")
	
	private String amountInWords;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel=false;
	@OneToMany(mappedBy = "salesVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesItemParticularsVO> salesItemParticularsVO;

	@OneToMany(mappedBy = "salesVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesOrderTermsVO> salesOrderTermsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
