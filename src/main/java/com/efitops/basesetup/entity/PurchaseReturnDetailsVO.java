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
@Table(name = "purchase_return_details")
public class PurchaseReturnDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_return_detailsgen")
    @SequenceGenerator(name = "purchase_return_detailsgen", sequenceName = "purchase_return_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_return_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "hsn_sac_code")
    private String hsnSacCode;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage;

    @Column(name = "tariff_no")
    private String tariffNo;

    @Column(name = "excise_to_post")
    private String exciseToPost;

    @Column(name = "challan_qty",precision = 10,scale = 2)
    private BigDecimal challanQty;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "grn_received_qty",precision = 10,scale = 2)
    private BigDecimal grnReceivedQty;

    @Column(name = "accepted_qty",precision = 10,scale = 2)
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty",precision = 10,scale = 2)
    private BigDecimal rejectedQty;

    @Column(name = "shortage_qty",precision = 10,scale = 2)
    private BigDecimal shortageQty;

    @Column(name = "po_rate",precision = 10,scale = 2)
    private BigDecimal poRate;

    @Column(name = "rate_in_inr",precision = 10,scale = 2)
    private BigDecimal rateInInr;

    @Column(name = "rate_in_selected_currency",precision = 10,scale = 2)
    private BigDecimal rateInSelectedCurrency;

    @Column(name = "apportioned_cost",precision = 10,scale = 2)
    private BigDecimal apportionedCost;

    @Column(name = "landed_cost_rate",precision = 10,scale = 2)
    private BigDecimal landedCostRate;

    @Column(name = "amount",precision = 10,scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_in_selected_currency",precision = 10,scale = 2)
    private BigDecimal amountInSelectedCurrency;

    @Column(name = "additional_duty",precision = 10,scale = 2)
    private BigDecimal additionalDuty;

    @Column(name = "amount_in_inr",precision = 10,scale = 2)
    private BigDecimal amountInInr;

    @Column(name = "sgst_rate",precision = 10,scale = 2)
    private BigDecimal sgstRate;

    @Column(name = "sgst_amount",precision = 10,scale = 2)
    private BigDecimal sgstAmount;

    @Column(name = "cgst_rate",precision = 10,scale = 2)
    private BigDecimal cgstRate;

    @Column(name = "cgst_amount",precision = 10,scale = 2)
    private BigDecimal cgstAmount;

    @Column(name = "igst_rate",precision = 10,scale = 2)
    private BigDecimal igstRate;

    @Column(name = "igst_amount",precision = 10,scale = 2)
    private BigDecimal igstAmount;
    
    
    @ManyToOne
    @JoinColumn(name = "purchase_return_basic_id")
    @JsonBackReference
    private PurchaseReturnVO purchaseReturnVO;
}
