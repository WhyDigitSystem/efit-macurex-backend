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
@Table(name = "purchase_contract_tax_details")
public class PurchaseContractTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_contract_tax_detailsgen")
    @SequenceGenerator(name = "purchase_contract_tax_detailsgen", sequenceName = "purchase_contract_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_contract_tax_details_id")
    private Long id;

    // entered by user
    @Column(name = "particulars")
    private String particulars;

    // entered by user
    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    // entered by user
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "purchase_contract_basic_id")
    @JsonBackReference
    private PurchaseContractVO purchaseContractVO;
}