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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill_tax_details")
public class PurchaseBillTaxGridVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_bill_tax_detailsgen")
    @SequenceGenerator(name = "purchase_bill_tax_detailsgen", sequenceName = "purchase_bill_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_bill_tax_details_id")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "accepted_qty_amount")
    private BigDecimal acceptedQtyAmount;

    @Column(name = "revised_amount")
    private BigDecimal revisedAmount;

    @Column(name = "ledger_account")
    private String ledgerAccount;

    @Column(name = "debit_credit")
    private String debitbCredit;

    @Column(name = "debit_amount")
    private BigDecimal debitAmount;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @Column(name = "post_to_finance_ac")
    private boolean postToFinanceAc;

    @ManyToOne
    @JoinColumn(name = "purchasebill_id")
    @JsonBackReference
    private PurchaseBillVO purchaseBillVO;
}