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
@Table(name = "purchase_bill_tax_grid")
public class PurchaseBillTaxGridVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebilltaxgridgen")
    @SequenceGenerator(name = "purchasebilltaxgridgen", sequenceName = "purchasebilltaxgridseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasebilltaxgrid_id")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "accepted_qty_amount")
    private BigDecimal acceptedQtyAmount;

    @Column(name = "revised_amount")
    private BigDecimal revisedAmount;

    // Ledger Account Name -> List Of Values, same pattern as Dealer Type/Tax Code
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_account_id")
    private ListOfValuesDetailsVO ledgerAccount;

    @Column(name = "db_cr")
    private String dbCr;

    @Column(name = "db_amt")
    private BigDecimal dbAmt;

    @Column(name = "cr_amt")
    private BigDecimal crAmt;

    @Column(name = "post_to_finance_ac")
    private Boolean postToFinanceAc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasebill_id")
    private PurchaseBillVO purchaseBillVO;
}