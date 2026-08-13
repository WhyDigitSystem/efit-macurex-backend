package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "purchase_delivery_schedule_line")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDeliveryScheduleLineVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasedeliveryschedulelinegen")
    @SequenceGenerator(
            name = "purchasedeliveryschedulelinegen",
            sequenceName = "purchasedeliveryschedulelineseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasedeliveryschedulelinegen_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "plan_date")
    private LocalDate planDate;

    @Column(name = "week_no")
    private Integer weekNo;

    @Column(name = "schedule_qty", precision = 10, scale = 2)
    private BigDecimal scheduleQty;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchasedeliveryschedule_id")
    private PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO;
}