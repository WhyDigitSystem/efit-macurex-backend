package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill_details")
public class PurchaseBillDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_bill_detailsgen")
    @SequenceGenerator(name = "purchase_bill_detailsgen", sequenceName = "purchase_bill_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_bill_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "hsn")
    private HsnVO hsnCode;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "tariff_no")
    private String tariffNo;

    @Column(name = "excise_to_post")
    private Boolean exciseToPost;

    @Column(name = "challan_qty")
    private BigDecimal challanQty;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "grn_received_qty")
    private BigDecimal grnReceivedQty;

    @Column(name = "accepted_qty")
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty")
    private BigDecimal rejectedQty;

    @Column(name = "shortage_qty")
    private BigDecimal shortageQty;

    @Column(name = "purchaseorder_rate")
    private BigDecimal purchaseorderRate;

    @Column(name = "rate_in_inr")
    private BigDecimal rateInInr;

    @Column(name = "rate_in_selected_currency")
    private BigDecimal rateInSelectedCurrency;

    @Column(name = "apportioned_cost")
    private BigDecimal apportionedCost;

    @Column(name = "landed_cost_rate")
    private BigDecimal landedCostRate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "amount_in_selected_currency")
    private BigDecimal amountInSelectedCurrency;

    @Column(name = "additional_duty")
    private BigDecimal additionalDuty;

    @Column(name = "amount_in_inr")
    private BigDecimal amountInInr;

    @Column(name = "sgst_rate")
    private BigDecimal sgstRate;

    @Column(name = "sgst_amount")
    private BigDecimal sgstAmount;

    @Column(name = "cgst_rate")
    private BigDecimal cgstRate;
    @Column(name = "cgst_amount")
    private BigDecimal cgstAmount;

    @Column(name = "igst_rate")
    private BigDecimal igstRate;
    @Column(name = "igst_amount")
    private BigDecimal igstAmount;

    @ManyToOne
    @JoinColumn(name = "purchasebill_id")
    @JsonBackReference
    private PurchaseBillVO purchaseBillVO;
}