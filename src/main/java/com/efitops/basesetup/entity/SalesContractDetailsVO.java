package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "sales_contract_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesContractDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sales_contract_detailsgen")
    @SequenceGenerator(name = "sales_contract_detailsgen",
            sequenceName = "sales_contract_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "salescontractdetails_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "tax_type")
    private String taxType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_percentage")
    private GSTRateMasterVO taxPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "quotation_rate")
    private BigDecimal quotationRate;

    @Column(name = "order_rate")
    private BigDecimal orderRate;
    
    
    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "amount")
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn(name = "gst_rate")
    private GSTRateMasterVO gstRate;

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
    
    @Column(name="final_amount")
    private BigDecimal finalAmount;

    @Column(name = "currency")
    private String currency;
    

    @ManyToOne
    @JoinColumn(name = "salescontract_id")
    @JsonBackReference
    private SalesContractVO salesContract;
}
