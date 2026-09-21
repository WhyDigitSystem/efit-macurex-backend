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
@Table(name = "reconcile_consumption_stock_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileConsumptionStockDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reconcile_consumption_stock_detailsgen")
    @SequenceGenerator(
            name = "reconcile_consumption_stock_detailsgen",
            sequenceName = "reconcile_consumption_stock_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "reconcile_consumption_stock_details_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "available_qty", precision = 15, scale = 5)
    private BigDecimal availableQty;

    @Column(name = "consumption_qty", precision = 15, scale = 5)
    private BigDecimal consumptionQty;

    
    @Column(name = "posted_qty", precision = 15, scale = 5)
    private BigDecimal postedQty;

    @Column(name = "difference_qty", precision = 15, scale = 5)
    private BigDecimal differenceQty;

    @Column(name = "rate", precision = 15, scale = 5)
    private BigDecimal rate;

    @Column(name = "value", precision = 15, scale = 5)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "reconcile_consumption_stock_id")
    @JsonBackReference
    private ReconcileConsumptionStockVO reconcileConsumptionStockVO;
}