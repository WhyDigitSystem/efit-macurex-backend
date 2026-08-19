package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_rejection_invoice_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceDetailsVO {

    // =========================
    // PRIMARY KEY
    // =========================

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sales_rejection_invoice_detailgen"
    )
    @SequenceGenerator(
            name = "sales_rejection_invoice_detailgen",
            sequenceName = "sales_rejection_invoice_detailseq",
            allocationSize = 1, initialValue = 1000000001
    )
    @Column(name = "sales_rejection_invoice_detail_id")
    private Long id;   


    // =========================
    // COMMON FIELDS
    // =========================

    @Column(name = "new_rate")
    private BigDecimal newRate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "hsn_sac_code")
    private String hsnSacCode;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage;

    @Column(name = "customer_part_no")
    private String customerPartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "stock")
    private String stock;

    @Column(name = "sales_order_contract_no")
    private String salesOrderContractNo;

    @Column(name = "despatch_qty")
    private BigDecimal despatchQty;

    @Column(name = "rate_in_selected_currency")
    private BigDecimal rateInSelectedCurrency;

    @Column(name = "amount_in_selected_currency")
    private BigDecimal amountInSelectedCurrency;

    @Column(name = "amount_in_rs")
    private BigDecimal amountInRs;


    // =========================
    // SGST
    // =========================

    @Column(name = "sgst_rate")
    private BigDecimal sgstRate;

    @Column(name = "sgst_amount")
    private BigDecimal sgstAmount;


    // =========================
    // CGST
    // =========================

    @Column(name = "cgst_rate")
    private BigDecimal cgstRate;

    @Column(name = "cgst_amount")
    private BigDecimal cgstAmount;


    // =========================
    // IGST
    // =========================

    @Column(name = "igst_rate")
    private BigDecimal igstRate;

    @Column(name = "igst_amount")
    private BigDecimal igstAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_rejection_invoice_basic_id")
    @JsonBackReference
    private SalesRejectionInvoiceVO salesRejectionInvoiceVO;

}
