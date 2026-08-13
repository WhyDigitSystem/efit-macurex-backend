package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "pcamddetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseContractAmendmentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "pcamddetail_seq")
    @SequenceGenerator(name = "pcamddetail_seq",
            sequenceName = "pcamddetail_seq",
            allocationSize = 1)
    @Column(name = "pcamddetail_id")
    private Long id;

    // Parent
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "pcamdbasic_id")

    private PurchaseContractAmendmentVO purchaseContractAmendment;
    // Item Code (Normalized with Item Master)
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    // Unit (Normalized with Unit Master)
    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "old_rate")
    private BigDecimal oldRate;

    @Column(name = "new_rate")
    private BigDecimal newRate;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "new_valid_from")
    private LocalDate newValidFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "new_valid_to")
    private LocalDate newValidTo;
}