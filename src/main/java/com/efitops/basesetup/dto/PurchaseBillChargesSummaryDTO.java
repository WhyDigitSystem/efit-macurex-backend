package com.efitops.basesetup.dto;

import java.math.BigDecimal;

public class PurchaseBillChargesSummaryDTO {

	private Long id;
	private Integer sNo;
	private String particulars;
	private BigDecimal taxPercent;
	private BigDecimal acceptedQtyAmount;
	private BigDecimal revisedAmount;
	private String ledgerAccountName;
	private String dbCr;
	private boolean postToFinance;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Integer getsNo() { return sNo; }
	public void setsNo(Integer sNo) { this.sNo = sNo; }
	public String getParticulars() { return particulars; }
	public void setParticulars(String particulars) { this.particulars = particulars; }
	public BigDecimal getTaxPercent() { return taxPercent; }
	public void setTaxPercent(BigDecimal taxPercent) { this.taxPercent = taxPercent; }
	public BigDecimal getAcceptedQtyAmount() { return acceptedQtyAmount; }
	public void setAcceptedQtyAmount(BigDecimal acceptedQtyAmount) { this.acceptedQtyAmount = acceptedQtyAmount; }
	public BigDecimal getRevisedAmount() { return revisedAmount; }
	public void setRevisedAmount(BigDecimal revisedAmount) { this.revisedAmount = revisedAmount; }
	public String getLedgerAccountName() { return ledgerAccountName; }
	public void setLedgerAccountName(String ledgerAccountName) { this.ledgerAccountName = ledgerAccountName; }
	public String getDbCr() { return dbCr; }
	public void setDbCr(String dbCr) { this.dbCr = dbCr; }
	public boolean isPostToFinance() { return postToFinance; }
	public void setPostToFinance(boolean postToFinance) { this.postToFinance = postToFinance; }
}
