package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_delivery_schedule_detailsgen")
    @SequenceGenerator(name = "purchase_delivery_schedule_detailsgen", sequenceName = "purchase_delivery_schedule_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_delivery_schedule_details_id")
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "primary_unit")
    private UnitMasterVO primaryUnit;
    
    @ManyToOne
    @JoinColumn(name = "purchase_unit")
    private UnitMasterVO purchaseUnit;

    @Column(name = "demand_qty")
    private BigDecimal demandQty;

    @Column(name = "available_stock")
    private BigDecimal availableStock;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "tentative_qty")
    private BigDecimal tentativeQty;

  
    @Column(name = "tentative_qty_next_month")
    private BigDecimal tentativeQtyNextMonth;

  
    @Column(name = "rate")
    private BigDecimal rate;
    
    @ManyToOne
    @JoinColumn(name = "purchase_delivery_schedule_basic_id")
    private PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO;

    @OneToMany(mappedBy = "purchaseDeliveryScheduleDetailsVO",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<PurchaseDeliveryScheduleLineVO> purchaseDeliveryScheduleLineVO = new ArrayList<>();
}