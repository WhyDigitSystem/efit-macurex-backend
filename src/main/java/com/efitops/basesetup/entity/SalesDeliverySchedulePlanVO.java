package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "dlryschedule")
@Data
public class SalesDeliverySchedulePlanVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "dlryschedule_seq")
    @SequenceGenerator(name = "dlryschedule_seq",sequenceName = "dlryschedule_seq",allocationSize = 1)
    @Column(name = "dlryschedule_id")
    private Long id;

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
    
    @ManyToOne
    @JoinColumn(name = "sdvdet_id")
    @JsonBackReference
    private SalesDeliveryScheduleDetailsVO salesDeliveryScheduleDetails;
}