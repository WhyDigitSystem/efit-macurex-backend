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
@Table(name = "purchase_bill_tax_grid")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseBillTaxGridVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebilltaxgridgen")
    @SequenceGenerator(
            name = "purchasebilltaxgridgen",
            sequenceName = "purchasebilltaxgridseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasebilltaxgrid_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "accepted_qty_amount", precision = 10, scale = 2)
    private BigDecimal acceptedQtyAmount;

    @Column(name = "revised_amount", precision = 10, scale = 2)
    private BigDecimal revisedAmount;

    @ManyToOne
    @JoinColumn(name = "ledger_account_id")
    private ListOfValuesDetailsVO ledgerAccount;

    @Column(name = "db_cr")
    private String dbCr;

    @Column(name = "db_amt", precision = 10, scale = 2)
    private BigDecimal dbAmt;

    @Column(name = "cr_amt", precision = 10, scale = 2)
    private BigDecimal crAmt;

    @Column(name = "post_to_finance_ac")
    private Boolean postToFinanceAc;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchasebill_id")
    private PurchaseBillVO purchaseBillVO;
}