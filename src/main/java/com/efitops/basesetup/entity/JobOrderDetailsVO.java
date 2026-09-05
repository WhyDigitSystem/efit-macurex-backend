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
@Table(name = "job_order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderDetailsVO {

    // =========================
    // PRIMARY KEY
    // =========================

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "job_order_detailsgen"
    )
    @SequenceGenerator(
            name = "job_order_detailsgen",
            sequenceName = "job_order_detailsseq",
            allocationSize = 1,
            initialValue = 1000000001
    )
    @Column(name = "job_order_details_id")
    private Long id;


    // =========================
    // INCOMING ITEM
    // =========================

    @ManyToOne
    @JoinColumn(name = "incoming_item")
    private ItemMasterVO incomingItem;

//    @ManyToOne
//    @JoinColumn(name = "bom_id")
//    private BomVO bom;

    @Column(name = "bom")
    private String bom;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "incoming_type")
    private String incomingType;


    @Column(name = "order_qty")
    private BigDecimal orderQty;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "amount")
    private BigDecimal amount;

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


    @Column(name = "sent_for")
    private String sentFor;
    
    
    @ManyToOne
    @JoinColumn(name = "job_order_basic_id")
    @JsonBackReference
    private JobOrderVO jobOrder;

}