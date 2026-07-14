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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseorderpending")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderPendingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseorderpendinggen")
	@SequenceGenerator(name = "purchaseorderpendinggen", sequenceName = "purchaseorderpendingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseorderpendingid")
	private Long id;
	@Column(name = "pono", length = 150)
	private String poNo;
	@Column(name="podate")
	private LocalDate poDate= LocalDate.now();
	@Column(name="customername")
	private String customerName;
	@Column(name="customercode")
	private String customerCode;
	@Column(name="workorderno")
	private String workOrderNo; 
	@Column(name="suppliername")
	private String supplierName;
	@Column(name="suppliercode")
	private String supplierCode;
	@Column(name = "sourceid")
	private Long sourceId;
	
	@Column(name = "item", length = 150)
	private String item;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="taxtype")
	private String taxType;
//	@Column(name="uom")
//	private String uom;
	@Column(name="price",precision = 10,scale = 2)
	private BigDecimal price; 
	@Column(name="qty",precision = 10,scale = 2)
	private BigDecimal qty;
	@Column(name="amount",precision = 10,scale = 2)
	private BigDecimal amount;
	@Column(name="taxvalue",precision = 10,scale = 2)
	private BigDecimal taxValue;
	@Column(name="landedvalue",precision = 10,scale = 2)
	private BigDecimal landedValue;
	@Column(name="plusorminus")
	private String plusOrMinus;
	
	
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
	private boolean cancel;
	@Column(name = "popendingcancel")
	private boolean poPendingCancel;

	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode",length = 30)
	private String screenCode ="POP";
	@Column(name = "screenname",length = 30)
	private String screenName="PurchaseOrderPending";
	
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
