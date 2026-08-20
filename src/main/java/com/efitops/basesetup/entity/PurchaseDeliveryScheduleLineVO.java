package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_delivery_schedule_plan_detail")
public class PurchaseDeliveryScheduleLineVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_delivery_schedule_plan_detailgen")
    @SequenceGenerator(name = "purchase_delivery_schedule_plan_detailgen", sequenceName = "purchase_delivery_schedule_plan_detailseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_delivery_schedule_plan_detail_id")
    private Long id;

   
    @Column(name = "plan_date")
    private LocalDate planDate;

  
    @Column(name = "week_no")
    private int weekNo;

   
    @Column(name = "schedule_qty")
    private BigDecimal scheduleQty;

    @ManyToOne
    @JoinColumn(name = "purchase_delivery_schedule_details_id")
    private PurchaseDeliveryScheduleDetailsVO purchaseDeliveryScheduleDetailsVO;
}