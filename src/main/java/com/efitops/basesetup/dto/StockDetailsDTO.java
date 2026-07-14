package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailsDTO {

	private Long id;
	private String cancel;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private String branchCode;
	private String docId;
	private LocalDate docDate;
	private int pickedQty;
	private BigDecimal rate;
	private double amount;
	private String inouttime;
	private String refNo;
	private LocalDate refDate;
	private int qty;
	private String lrhawbhblNo;
	private String client;
	private LocalDate stockDate=LocalDate.now();
	private String grnNo;
	private String carrier;
	private int oQty;
	private int rQty;
	private int dQty;
	private int cQty;
	private int uQty;
	private String branch;
	private String partno;
	private String partDesc;
	private String sourceScreenCode;
	private String sourceScreenName;
	private String remarks;
	private String customer;
	private String binType;
	private String cellType;
	private String core;
	private String bin;
	private String warehouse;
	private String sku;
	private String sSku;
	private int ssQty;
	private Long sourceId;
	private int sQty;
	private int pQty;
	private int invQty;
	private int recQty;
	private int damageQty;
	private LocalDate grnDate;
	private int shortQty;
	private String qcFlag;
	private String clientCode;
	private double pAmount;
	private String binClass;
	private String pGroup;
	private String barCode;
	private String styleCode;
	private LocalDate expDate;
	private String buyerOrderNo;
	private String batch;
	private LocalDate batchDate;
	private double weight;
	private String pcKey;
	private String sdactual;
	private String tPartNo;
	private LocalDate sDate;
	private LocalDate cDocDate;
	private String status;
	private String invoiceNo;
	private String iStatus;
	private String sFlag;
	private String lotNo;
	private boolean active;
	private String updatedBy;
//	private String screenCode;
	private String finYear;
	private boolean stockFreeze=false;
	private String stockPreviousStatus;

	
}
