package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebilldetailsgen")
    @SequenceGenerator(name = "purchasebilldetailsgen", sequenceName = "purchasebilldetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasebilldetails_id")
    private Long id;

    // Item Code -> Item Description/HSN/Unit all resolved from the same ItemMaster record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "tariff_no")
    private String tariffNo;

    @Column(name = "excise_to_post")
    private Boolean exciseToPost;

    @Column(name = "challan_qty")
    private BigDecimal challanQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    // TODO: link to real GRN line once that module exists
    @Column(name = "grn_received_qty")
    private BigDecimal grnReceivedQty;

    @Column(name = "accepted_qty")
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty")
    private BigDecimal rejectedQty;

    @Column(name = "shortage_qty")
    private BigDecimal shortageQty;

    @Column(name = "po_rate")
    private BigDecimal poRate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasebill_id")
    private PurchaseBillVO purchaseBillVO;
}