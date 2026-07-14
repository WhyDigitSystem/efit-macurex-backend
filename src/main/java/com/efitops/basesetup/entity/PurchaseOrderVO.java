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
@Table(name = "purchaseorder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseordergen")
	@SequenceGenerator(name = "purchaseordergen", sequenceName = "purchaseorderseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseorderid",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name="podate")
	private LocalDate poDate= LocalDate.now();
	@Column(name="customername")
	private String customerName;
	@Column(name="customercode")
	private String customerCode;
	@Column(name="workorderno")
	private String workOrderNo;
	@Column(name="basedon")
	private String basedOn;
	@Column(name="quotationno")
	private String quotationNo;
	@Column(name="purchaseindentno")
	private String purchaseIndentNo; 
	@Column(name="suppliername")
	private String supplierName;
	@Column(name="suppliercode")
	private String supplierCode;
	@Column(name="contactperson")
	private String contactPerson;
	@Column(name="mobileno")
	private long mobileNo;
	@Column(name="email")
	private String email;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="country")
	private String country; 
	@Column(name="taxcode")
	private String taxCode; 
	@Column(name="address")
	private String address; 
	@Column(name="grossamount",precision = 10,scale = 2)
	private BigDecimal grossAmount;
	@Column(name="netamount",precision = 10,scale = 2)
	private BigDecimal netAmount;
	@Column(name="totallandedamount",precision = 10,scale = 2)
	private BigDecimal totalLandedAmount;
	@Column(name="toatlamounttax",precision = 10,scale = 2)
	private BigDecimal totalAmountTax;
	@Column(name="amtinwords")
	private String amtInWords;
	@Column(name="remarks")
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
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name="popendingcancel")
	private boolean poPendingCancel;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode",length = 30)
	private String screenCode ="PO";
	@Column(name = "screenname",length = 30)
	private String screenName="PURCHASEORDER";
	
	
	@OneToMany(mappedBy = "purchaseOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<PurchaseOrderDetailsVO> purchaseOrderDetailsVO;
	
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
