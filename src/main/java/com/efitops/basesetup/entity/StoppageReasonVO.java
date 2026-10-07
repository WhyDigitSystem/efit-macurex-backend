package com.efitops.basesetup.entity;


import java.math.BigDecimal;

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
@Table(name = "stoppage_reason")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoppageReasonVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoppage_reasongen")
    @SequenceGenerator(name = "stoppage_reasongen", sequenceName = "stoppage_reasonseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "stoppage_reason_id")
    private Long id;

    @Column(name = "fr_time_hrs", precision = 10, scale = 2)
    private BigDecimal frTimeHrs;

    @Column(name = "fr_time_mins", precision = 10, scale = 2)
    private BigDecimal frTimeMins;

    @Column(name = "to_time_hrs", precision = 10, scale = 2)
    private BigDecimal toTimeHrs;

    @Column(name = "to_time_mins", precision = 10, scale = 2)
    private BigDecimal toTimeMins;

    @Column(name = "tot_time_in_mins", precision = 10, scale = 2)
    private BigDecimal totTimeInMins;

    @ManyToOne
    @JoinColumn(name = "reason")
    private ReasonMasterVO reason;


    @Column(name = "stoppage_mc_cost", precision = 10, scale = 2)
    private BigDecimal stoppageMcCost;

    @Column(name = "stoppage_labour_cost", precision = 10, scale = 2)
    private BigDecimal stoppageLabourCost;

    @Column(name = "remarks")
    private String remarks;
    

    @ManyToOne
    @JoinColumn(name = "production_entry_basic_id")
    @JsonBackReference
    private ProductionEntryVO productionEntryVO;
}
