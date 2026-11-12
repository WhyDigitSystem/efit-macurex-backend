package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subcontract_supply_schedule_item_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleItemDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontract_supply_schedule_item_detailsgen")
    @SequenceGenerator(
            name = "subcontract_supply_schedule_item_detailsgen",
            sequenceName = "subcontract_supply_schedule_item_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "subcontract_supply_schedule_item_details_id")
    private Long id;


    // =========================
    // Item Details
    // =========================

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO Unit;

    @Column(name = "stock")
    private BigDecimal stock;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "rate")
    private BigDecimal rate;


    // =========================
    // Parent
    // =========================

    @ManyToOne
    @JoinColumn(name = "subcontract_supply_schedule_id")
    @JsonBackReference
    private SubContractSupplyScheduleVO subContractSupplyScheduleVO;


    // =========================
    // Schedule Details
    // =========================

    @OneToMany(
            mappedBy = "itemDetails",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<SubContractSupplyScheduleDetailsVO> scheduleDetails;
}