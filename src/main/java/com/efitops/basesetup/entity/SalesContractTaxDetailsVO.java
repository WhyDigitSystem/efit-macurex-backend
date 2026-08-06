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
@Table(name = "sales_contract_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesContractTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "salescontracttaxgen")
    @SequenceGenerator(
            name = "salescontracttaxgen",
            sequenceName = "salescontracttaxseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "sales_contract_tax_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "particulars")
    private ListOfValuesDetailsVO particulars;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn(name = "salescontract_id")
    @JsonBackReference
    private SalesContractVO salesContract;
}