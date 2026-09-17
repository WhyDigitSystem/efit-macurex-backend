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
@Table(name = "sub_contracting_grn_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_contracting_grn_tax_detailsgen")
    @SequenceGenerator(
            name = "sub_contracting_grn_tax_detailsgen",
            sequenceName = "sub_contracting_grn_tax_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "sub_contracting_grn_tax_details_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_amount", precision = 15, scale = 5)
    private BigDecimal taxAmount;

    @ManyToOne
    @JoinColumn(name = "sub_contracting_grn_id")
    @JsonBackReference
    private SubContractingGRNVO subContractingGRNVO;
}