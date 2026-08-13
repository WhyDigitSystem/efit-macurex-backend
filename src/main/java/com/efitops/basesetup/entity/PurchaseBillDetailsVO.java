package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_bill_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseBillDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebilldetailsgen")
    @SequenceGenerator(
            name = "purchasebilldetailsgen",
            sequenceName = "purchasebilldetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasebilldetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    @ManyToOne
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "tariff_no")
    private String tariffNo;

    @Column(name = "excise_to_post")
    private Boolean exciseToPost;

    @Column(name = "challan_qty", precision = 10, scale = 2)
    private BigDecimal challanQty;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    @Column(name = "grn_received_qty", precision = 10, scale = 2)
    private BigDecimal grnReceivedQty;

    @Column(name = "accepted_qty", precision = 10, scale = 2)
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty", precision = 10, scale = 2)
    private BigDecimal rejectedQty;

    @Column(name = "shortage_qty", precision = 10, scale = 2)
    private BigDecimal shortageQty;

    @Column(name = "po_rate", precision = 10, scale = 2)
    private BigDecimal poRate;

    @Column(name = "rate_in_inr", precision = 10, scale = 2)
    private BigDecimal rateInInr;

    @Column(name = "rate_in_selected_currency", precision = 10, scale = 2)
    private BigDecimal rateInSelectedCurrency;

    @Column(name = "apportioned_cost", precision = 10, scale = 2)
    private BigDecimal apportionedCost;

    @Column(name = "landed_cost_rate", precision = 10, scale = 2)
    private BigDecimal landedCostRate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_in_selected_currency", precision = 10, scale = 2)
    private BigDecimal amountInSelectedCurrency;

    @Column(name = "additional_duty", precision = 10, scale = 2)
    private BigDecimal additionalDuty;

    @Column(name = "amount_in_inr", precision = 10, scale = 2)
    private BigDecimal amountInInr;

    @Column(name = "sgst_rate", precision = 10, scale = 2)
    private BigDecimal sgstRate;

    @Column(name = "sgst_amount", precision = 10, scale = 2)
    private BigDecimal sgstAmount;

    @Column(name = "cgst_rate", precision = 10, scale = 2)
    private BigDecimal cgstRate;

    @Column(name = "cgst_amount", precision = 10, scale = 2)
    private BigDecimal cgstAmount;

    @Column(name = "igst_rate", precision = 10, scale = 2)
    private BigDecimal igstRate;

    @Column(name = "igst_amount", precision = 10, scale = 2)
    private BigDecimal igstAmount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchasebill_id")
    private PurchaseBillVO purchaseBillVO;
}