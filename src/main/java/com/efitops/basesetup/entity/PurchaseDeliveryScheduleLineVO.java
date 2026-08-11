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
@Table(name = "purchase_delivery_schedule_line")
public class PurchaseDeliveryScheduleLineVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasedeliveryschedulelinegen")
    @SequenceGenerator(name = "purchasedeliveryschedulelinegen", sequenceName = "purchasedeliveryschedulelineseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasedeliveryschedulelinegen_id")
    private Long id;

    // entered by user
    @Column(name = "plan_date")
    private LocalDate planDate;

    // entered by user - assumed numeric; change to String if shown as "W1", "W2" etc.
    @Column(name = "week_no")
    private Integer weekNo;

    // entered by user
    @Column(name = "schedule_qty")
    private BigDecimal scheduleQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasedeliveryschedule_id")
    private PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO;
}