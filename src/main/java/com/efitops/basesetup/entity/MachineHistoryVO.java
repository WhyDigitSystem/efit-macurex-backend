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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "machine_history_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineHistoryVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "machine_history_detailgen" )
    @SequenceGenerator( name = "machine_history_detailgen", sequenceName = "machine_history_detailseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "machine_history_detail_id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @Column(name = "changed_date")
    private LocalDate changedDate;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "machine_equipments_master_id")
    @JsonBackReference
    private MachineMasterVO machineMasterVO;

}