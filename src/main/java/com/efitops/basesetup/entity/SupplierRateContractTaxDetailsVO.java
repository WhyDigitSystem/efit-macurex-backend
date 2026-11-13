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
@Table(name = "supplier_rate_contract_tax_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractTaxDetailsVO {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supplier_rate_contract_tax_detailsgen"
    )
    @SequenceGenerator(
            name = "supplier_rate_contract_tax_detailsgen",
            sequenceName = "supplier_rate_contract_tax_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "supplier_rate_contract_tax_details_id")
    private Long id;


    @Column(name = "particulars")
    private String particulars;


    @Column(name = "amount")
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "supplier_rate_contract_id")
    @JsonBackReference
    private SupplierRateContractVO supplierRateContractVO;

}