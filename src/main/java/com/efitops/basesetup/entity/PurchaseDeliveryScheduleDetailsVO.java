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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_delivery_schedule_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDeliveryScheduleDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasedeliveryscheduledetailsgen")
    @SequenceGenerator(
            name = "purchasedeliveryscheduledetailsgen",
            sequenceName = "purchasedeliveryscheduledetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasedeliveryscheduledetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "primary_unit_id")
    private UnitMasterVO primaryUnit;

    @ManyToOne
    @JoinColumn(name = "purchase_unit_id")
    private UnitMasterVO purchaseUnit;

    @Column(name = "demand_qty", precision = 10, scale = 2)
    private BigDecimal demandQty;

    @Column(name = "available_stock", precision = 10, scale = 2)
    private BigDecimal availableStock;

    @Column(name = "qty", precision = 10, scale = 2)
    private BigDecimal qty;

    @Column(name = "tentative_qty", precision = 10, scale = 2)
    private BigDecimal tentativeQty;

    @Column(name = "tentative_qty_next_month", precision = 10, scale = 2)
    private BigDecimal tentativeQtyNextMonth;

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal rate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchasedeliveryschedule_id")
    private PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO;
}