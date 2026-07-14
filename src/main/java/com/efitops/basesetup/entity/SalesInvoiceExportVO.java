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
@Table(name = "salesinvoiceexport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceExportVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesinvoiceexportgen")
	@SequenceGenerator(name = "salesinvoiceexportgen", sequenceName = "salesinvoiceexportseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesinvoiceexportid")
	private Long id;
	
	@Column(name ="docid")
	private String docId;
	@Column(name ="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name ="customername")
	private String customerName;
	@Column(name ="salesorderno")
	private String salesOrderNo;
	@Column(name ="exportpackingno")
	private String exportPackingNo;
	private String currency;
	@Column(name ="exchangerate")
	private Long exchangeRate;
	@Column(name="location")
	private String location;
	@Column(name ="billingaddress")
	private String billingAddress;
	@Column(name ="shippingaddress")
	private String shippingAddress;
	
	@Column(name = "orgid", length = 15)
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "cancel")
	private boolean cancel =false;
	@Column(name = "cancelremarks", length = 25)
	private String cancelRemarks;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "screencode", length = 10)
	private String screenCode = "SIE";
	@Column(name = "screenname", length = 25)
	private String screenName = "SALES INVOICE EXPORT";
	
	@Column(name ="totalqty", precision = 10, scale = 2)
	private BigDecimal totalQty;
	@Column(name ="totalamount", precision = 10, scale = 2)
	private BigDecimal totalAmount;
	@Column(name ="totalgrossamount", precision = 10, scale = 2)
	private BigDecimal totalGrossAmount;
	@Column(name ="totaldiscountamount", precision = 10, scale = 2)
	private BigDecimal totalDiscountAmount;
	@Column(name ="totalamountinwords")
	private String totalAmountInWords;
	@Column(name="remarks")
	private String remarks;
	
	
	@OneToMany(mappedBy ="salesInvoiceExportVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesInvoiceExportDetailsVO> salesInvoiceExportDetailsVO;
	
	@OneToMany(mappedBy ="salesInvoiceExportVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesInvoiceExportTermsVO> salesInvoiceExportTermsVO;
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
