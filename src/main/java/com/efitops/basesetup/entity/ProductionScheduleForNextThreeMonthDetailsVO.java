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
@Table(name = "production_schedule_for_next_three_month_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "productionschedulefornextthreemonthdetailsgen")
    @SequenceGenerator(
            name = "productionschedulefornextthreemonthdetailsgen",
            sequenceName = "productionschedulefornextthreemonthdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "production_schedule_for_next_three_month_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "production_schedule_for_next_three_month_id")
    @JsonBackReference
    private ProductionScheduleForNextThreeMonthVO
            productionScheduleForNextThreeMonth;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "date")
    private LocalDate date;

    
    @Column(name = "january", precision = 18, scale = 2)
    private BigDecimal january;

    @Column(name = "february", precision = 18, scale = 2)
    private BigDecimal february;

    @Column(name = "march", precision = 18, scale = 2)
    private BigDecimal march;

    @Column(name = "april", precision = 18, scale = 2)
    private BigDecimal april;

    @Column(name = "may", precision = 18, scale = 2)
    private BigDecimal may;

    @Column(name = "june", precision = 18, scale = 2)
    private BigDecimal june;

    @Column(name = "july", precision = 18, scale = 2)
    private BigDecimal july;

    @Column(name = "august", precision = 18, scale = 2)
    private BigDecimal august;

    @Column(name = "september", precision = 18, scale = 2)
    private BigDecimal september;

    @Column(name = "october", precision = 18, scale = 2)
    private BigDecimal october;

    @Column(name = "november", precision = 18, scale = 2)
    private BigDecimal november;

    @Column(name = "december", precision = 18, scale = 2)
    private BigDecimal december;
}