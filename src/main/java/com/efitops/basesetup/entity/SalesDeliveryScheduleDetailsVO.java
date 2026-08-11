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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "sdvdet")
@Data
public class SalesDeliveryScheduleDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sdvdet_seq")
    @SequenceGenerator(name = "sdvdet_seq",sequenceName = "sdvdet_seq", allocationSize = 1, initialValue = 1000000001)
    @Column(name = "sdvdet_id")
    private Long id;

    // Sales Contract Header
//    @ManyToOne
//    @JoinColumn(name = "sales_contract_id")
//    private SalesContractVO salesContract;

    // Sales Contract Detail (Invoice Type comes from selected SO)
//    @ManyToOne
//    @JoinColumn(name = "sales_contract_details_id")
//    private SalesContractDetailsVO salesContractDetails;

    @Column(name = "soNocontractno")
    private String soNoContractNo;
    
    @Column(name = "invoicetype")
    private String invoiceType;
    
    // Item Master
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @Column(name = "actual_planned_qty")
    private Double actualPlannedQty;
    
    @ManyToOne
    @JoinColumn(name = "sdvbasic_id")
    @JsonBackReference
    private SalesDeliveryScheduleVO salesDeliverySchedule;
    
    @OneToMany(
            mappedBy = "salesDeliveryScheduleDetails",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SalesDeliverySchedulePlanVO> deliverySchedules = new ArrayList<>();
}