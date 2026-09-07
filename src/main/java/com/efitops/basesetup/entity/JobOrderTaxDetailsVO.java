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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_order_tax_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderTaxDetailsVO {

    // =========================
    // PRIMARY KEY
    // =========================

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "job_order_tax_detailsgen"
    )
    @SequenceGenerator(
            name = "job_order_tax_detailsgen",
            sequenceName = "job_order_tax_detailsseq",
            allocationSize = 1,
            initialValue = 1000000001
    )
    @Column(name = "job_order_tax_details_id")
    private Long id;


    @Column(name = "particulars")
    private String particulars;


    @Column(name = "amount")
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "job_order_basic_id")
    @JsonBackReference
    private JobOrderVO jobOrder;

}