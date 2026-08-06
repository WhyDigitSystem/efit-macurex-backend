package com.efitops.basesetup.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sales_delivery_schedule_details")
@Data
public class SalesDeliveryScheduleDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_delivery_schedule_details_seq")
    @SequenceGenerator(name = "sales_delivery_schedule_details_seq",sequenceName = "sales_delivery_schedule_details_seq", allocationSize = 1)
    @Column(name = "sales_delivery_schedule_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_delivery_schedule_id")
    private SalesDeliveryScheduleVO salesDeliverySchedule;

    // Sales Contract Header
    @ManyToOne
    @JoinColumn(name = "sales_contract_id")
    private SalesContractVO salesContract;

    // Sales Contract Detail (Invoice Type comes from selected SO)
    @ManyToOne
    @JoinColumn(name = "sales_contract_details_id")
    private SalesContractDetailsVO salesContractDetails;

    // Item Master
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @Column(name = "actual_planned_qty")
    private Double actualPlannedQty;
    
    @OneToMany(
            mappedBy = "salesDeliveryScheduleDetails",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SalesDeliverySchedulePlanVO> deliverySchedules = new ArrayList<>();
}