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
@Table(name = "stock_order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOrderDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_order_detailsgen")
    @SequenceGenerator(
            name = "stock_order_detailsgen",
            sequenceName = "stock_order_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "stock_order_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "required_qty", precision = 15, scale = 5)
    private BigDecimal requiredQty;

    @Column(name = "rate", precision = 15, scale = 5)
    private BigDecimal rate;

    @Column(name = "amount", precision = 15, scale = 5)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "stock_order_id")
    @JsonBackReference
    private StockOrderVO stockOrderVO;
}