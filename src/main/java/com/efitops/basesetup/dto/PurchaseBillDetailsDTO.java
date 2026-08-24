package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillDetailsDTO {

	private Long item;

	private Long hsnCode;

	private String taxType;

	private BigDecimal taxPercent;

	private String tariffNo;

	private Boolean exciseToPost;

	private BigDecimal challanQty;

	private Long unit;

	private BigDecimal grnReceivedQty;

	private BigDecimal acceptedQty;

	private BigDecimal rejectedQty;

	private BigDecimal shortageQty;

	private BigDecimal purchaseorderRate;

	private BigDecimal rateInInr;

	private BigDecimal rateInSelectedCurrency;

	private BigDecimal apportionedCost;

	private BigDecimal landedCostRate;

	private BigDecimal amount;

	private BigDecimal amountInSelectedCurrency;

	private BigDecimal additionalDuty;

	private BigDecimal amountInInr;

	private BigDecimal sgstRate;

	private BigDecimal sgstAmount;

	private BigDecimal cgstRate;

	private BigDecimal cgstAmount;

	private BigDecimal igstRate;

	private BigDecimal igstAmount;
}