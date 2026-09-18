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
@Table(name = "delivery_challan_cum_gate_pass_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCumGatePassDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_challan_cum_gate_pass_detailsgen")
    @SequenceGenerator(
            name = "delivery_challan_cum_gate_pass_detailsgen",
            sequenceName = "delivery_challan_cum_gate_pass_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "delivery_challan_cum_gate_pass_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "hsn_sac_code")
    private HsnVO hsnSacCode;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "stock", precision = 10, scale = 2)
    private BigDecimal stock;

    @Column(name = "available_qty", precision = 10, scale = 2)
    private BigDecimal availableQty;

    @Column(name = "qty", precision = 10, scale = 2)
    private BigDecimal qty;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "previous_qty", precision = 10, scale = 2)
    private BigDecimal previousQty;

    @Column(name = "lc_rate", precision = 10, scale = 2)
    private BigDecimal lcRate;

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "delivery_challan_cum_gate_pass_id")
    private DeliveryChallanCumGatePassVO deliveryChallanCumGatePassVO;
}