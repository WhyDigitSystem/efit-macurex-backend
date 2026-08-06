package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "sales_delivery_schedule_plan")
@Data
public class SalesDeliverySchedulePlanVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_delivery_schedule_plan_seq")
    @SequenceGenerator(name = "sales_delivery_schedule_plan_seq",sequenceName = "sales_delivery_schedule_plan_seq",allocationSize = 1)
    @Column(name = "sales_delivery_schedule_plan_id")
    private Long id;

    // Header
    @ManyToOne
    @JoinColumn(name = "sales_delivery_schedule_id")
    private SalesDeliveryScheduleVO salesDeliverySchedule;

    // Schedule Details Grid
    @ManyToOne
    @JoinColumn(name = "sales_delivery_schedule_details_id")
    private SalesDeliveryScheduleDetailsVO salesDeliveryScheduleDetails;

    @Column(name = "day_no")
    private Integer dayNo;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "week_no")
    private Integer weekNo;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "delivery_qty")
    private Double deliveryQty;
}