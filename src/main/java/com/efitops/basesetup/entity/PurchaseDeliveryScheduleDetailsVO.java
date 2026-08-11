package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_delivery_schedule_details")
public class PurchaseDeliveryScheduleDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasedeliveryscheduledetailsgen")
    @SequenceGenerator(name = "purchasedeliveryscheduledetailsgen", sequenceName = "purchasedeliveryscheduledetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasedeliveryscheduledetails_id")
    private Long id;

    // Item Code -> ItemMaster; the 5 fields below are all snapshotted from THIS item at save time
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_unit_id")
    private UnitMasterVO primaryUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_unit_id")
    private UnitMasterVO purchaseUnit;

    @Column(name = "demand_qty")
    private BigDecimal demandQty;

    @Column(name = "available_stock")
    private BigDecimal availableStock;

    @Column(name = "qty")
    private BigDecimal qty;

    // entered by user
    @Column(name = "tentative_qty")
    private BigDecimal tentativeQty;

    // entered by user
    @Column(name = "tentative_qty_next_month")
    private BigDecimal tentativeQtyNextMonth;

    // entered by user
    @Column(name = "rate")
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasedeliveryschedule_id")
    private PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO;
}