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
@Table(name = "subcontract_supply_schedule_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontract_supply_schedule_detailsgen")
    @SequenceGenerator(
            name = "subcontract_supply_schedule_detailsgen",
            sequenceName = "subcontract_supply_schedule_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "subcontract_supply_schedule_details_id")
    private Long id;


    // =========================
    // Schedule Details
    // =========================

    @Column(name = "plan_date")
    private LocalDate planDate;

    @Column(name = "schedule_qty")
    private BigDecimal scheduleQty;


    // =========================
    // Parent Item Details
    // =========================

    @ManyToOne
    @JoinColumn(name = "subcontract_supply_schedule_item_details_id")
    @JsonBackReference
    private SubContractSupplyScheduleItemDetailsVO itemDetails;
}