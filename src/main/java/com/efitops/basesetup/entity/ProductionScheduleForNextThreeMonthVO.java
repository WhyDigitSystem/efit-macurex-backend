package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production_schedule_for_next_three_month")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "productionschedulefornextthreemonthgen")
    @SequenceGenerator(
            name = "productionschedulefornextthreemonthgen",
            sequenceName = "productionschedulefornextthreemonthseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "production_schedule_for_next_three_month_id")
    private Long id;
    
    @Column(name = "month_year")
    private String monthYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_code", length = 30)
    private String screenCode = "PSNTM";

    @Column(name = "screen_name", length = 100)
    private String screenName =
            "PRODUCTION SCHEDULE FOR NEXT THREE MONTHS";

    @OneToMany(
            mappedBy = "productionScheduleForNextThreeMonth",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductionScheduleForNextThreeMonthDetailsVO>
            productionScheduleForNextThreeMonthDetails =
            new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate =
            new CreatedUpdatedDate();
}
