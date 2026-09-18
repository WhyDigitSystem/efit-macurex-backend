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
@Table(name = "import_purchase_bill_charges_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillChargesSummaryVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "import_purchase_bill_charges_summary_seq")
    @SequenceGenerator(
            name = "import_purchase_bill_charges_summary_seq",
            sequenceName = "import_purchase_bill_charges_summary_seq",
            initialValue = 1000000001,
            allocationSize = 1)
    private Long id;

    @Column(name = "tot_fob_value_fc")
    private BigDecimal totFobValueFc;

    @Column(name = "tot_fob_value_inr")
    private BigDecimal totFobValueInr;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "tot_fri_ins_fc")
    private BigDecimal totFriInsFc;

    @Column(name = "tot_duty_inr")
    private BigDecimal totDutyInr;

    @Column(name = "post_voucher")
    private Boolean postVoucher;

    @Column(name = "total_value_fc")
    private BigDecimal totalValueFc;

    @Column(name = "tot_fre_ins_inr")
    private BigDecimal totFreInsInr;

    @Column(name = "tot_land_cost")
    private BigDecimal totLandCost;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "narration")
    private String narration;

    @ManyToOne
    @JoinColumn(name = "purchasebill_id")
    @JsonBackReference
    private PurchaseBillVO purchaseBillVO;
}