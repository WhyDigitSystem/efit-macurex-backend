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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "import_purchase_bill_details")
public class ImportPurchaseBillDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "import_purchase_bill_detailsgen")
    @SequenceGenerator(name = "import_purchase_bill_detailsgen", sequenceName = "import_purchase_bill_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "import_purchase_bill_details_id")
    private Long id;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "challan_qty", precision = 18, scale = 3)
    private BigDecimal challanQty;

    @Column(name = "grn_qty", precision = 18, scale = 3)
    private BigDecimal grnQty;

    @Column(name = "accpt_qty", precision = 18, scale = 3)
    private BigDecimal accptQty;

    @Column(name = "shortage_qty", precision = 18, scale = 3)
    private BigDecimal shortageQty;

    @Column(name = "fob_rate_fc", precision = 18, scale = 4)
    private BigDecimal fobRateFc;

    @Column(name = "fob_value_fc", precision = 18, scale = 4)
    private BigDecimal fobValueFc;

    @Column(name = "fob_value_inr", precision = 18, scale = 4)
    private BigDecimal fobValueInr;

    @Column(name = "duty_amt_inr", precision = 18, scale = 4)
    private BigDecimal dutyAmtInr;

    @Column(name = "value_fc", precision = 18, scale = 4)
    private BigDecimal valueFc;

    @Column(name = "value_inr", precision = 18, scale = 4)
    private BigDecimal valueInr;

    @Column(name = "land_cost_inr", precision = 18, scale = 4)
    private BigDecimal landCostInr;

    @ManyToOne
    @JoinColumn(name = "purchasebill_id")
    @JsonBackReference
    private PurchaseBillVO purchaseBillVO;
    
    
}
