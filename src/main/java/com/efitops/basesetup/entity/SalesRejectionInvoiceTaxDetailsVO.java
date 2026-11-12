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
@Table(name = "sales_rejection_invoice_tax_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceTaxDetailsVO {

    // =========================
    // PRIMARY KEY
    // =========================

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sales_rejection_invoice_tax_detailgen"
    )
    @SequenceGenerator(
            name = "sales_rejection_invoice_tax_detailgen",
            sequenceName = "sales_rejection_invoice_tax_detailseq",
            allocationSize = 1,initialValue = 1000000001
    )
    @Column(name = "sales_rejection_invoice_tax_detail_id")
    private Long id;


    // =========================
    // COMMON FIELDS
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "particulars")
    private ListOfValuesDetailsVO particulars;

    @Column(name = "gl_account_name")
    private String glAccountName;


    // =========================
    // SALES / REJECTION INVOICE
    // =========================

    @Column(name = "accepted_qty_amount", precision = 18, scale = 2)
    private BigDecimal acceptedQtyAmount;

    @Column(name = "revised_amount", precision = 18, scale = 2)
    private BigDecimal revisedAmount;


    // =========================
    // DC CUM INVOICE
    // =========================

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;
    
    // =========================
    // PARENT
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_rejection_invoice_basic_id")
    @JsonBackReference
    private SalesRejectionInvoiceVO salesRejectionInvoiceVO;
}