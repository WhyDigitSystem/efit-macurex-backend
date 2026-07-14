package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "deletestock")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeleteStockVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deletestockgen")
	@SequenceGenerator(name = "deletestockgen", sequenceName = "deletestockseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deletestockid")
	private Long id;
	@Column(name = "cancel")
	private String cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "docid", length = 25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "rate")
	private BigDecimal rate;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "inouttime")
	private LocalTime inouttime = LocalTime.now();
	@Column(name = "refno", length = 25)
	private Long refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "plusorminus")
	private String plusOrMinus;
	@Column(name = "client", length = 150)
	private String client;
	@Column(name = "stockdate")
	private LocalDate stockDate = LocalDate.now();
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partdesc", length = 150)
	private String partDesc;
	@Column(name = "sourcescreencode", length = 10)
	private String sourceScreenCode;
	@Column(name = "sourcescreenname", length = 25)
	private String sourceScreenName;
	@Column(name = "remarks", length = 150)
	private String remarks;
	@Column(name = "customer", length = 150)
	private String customer;
	@Column(name = "sourceid")
	private Long sourceId;
//		@Column(name = "issueqty",precision = 10,scale = 2)
//		private BigDecimal issueQty;
	@Column(name = "recqty", precision = 10, scale = 2)
	private BigDecimal recQty;
	@Column(name = "qcflag", length = 10)
	private String qcFlag;
	@Column(name = "clientcode", length = 25)
	private String clientCode;
	@Column(name = "batch", length = 25)
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "weight")
	private double weight;
	@Column(name = "sdate")
	private LocalDate sDate;
	@Column(name = "location")
	private String location;
	@Column(name = "cdocdate")
	private LocalDate cDocDate;
	@Column(name = "status", length = 10)
	private String status;
	@Column(name = "invoiceno", length = 25)
	private String invoiceNo;
	@Column(name = "isstatus", length = 10)
	private String iStatus;
	@Column(name = "active")
	private String active;
//		@Column(name = "cancel")
//		private boolean cancel;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "suppliername")
	private String supplierName;
	@Column(name = "suppliercode")
	private String supplierCode;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "screencode")
	private String screenCode = "DS";
	@Column(name = "screenname")
	private String screenName = "DELETE STOCK";
	@Column(name = "finyear")
	private String finYear;
//		@Column(name="stockfreeze")
//		private boolean stockFreeze=false;
//		@Column(name="stockpreviousstatus",length =25)
//		private String stockPreviousStatus;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
