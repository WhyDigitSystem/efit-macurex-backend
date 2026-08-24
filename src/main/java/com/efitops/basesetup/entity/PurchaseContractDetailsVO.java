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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_contract_details")
public class PurchaseContractDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_contract_detailsgen")
    @SequenceGenerator(name = "purchase_contract_detailsgen", sequenceName = "purchase_contract_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_contract_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;
    
    @Column(name = "hsn_code")
    private String hscCode;
   
    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percentage")
    private String taxPercentage;
   
    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    // entered by user
    @Column(name = "rate_in_currency")
    private BigDecimal rateInCurrency;

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

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne
    @JoinColumn(name = "purchase_contract_basic_id")
    @JsonBackReference
    private PurchaseContractVO purchaseContractVO;
}