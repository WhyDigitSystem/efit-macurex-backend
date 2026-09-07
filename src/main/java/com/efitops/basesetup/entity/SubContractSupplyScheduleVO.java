package com.efitops.basesetup.entity;

import java.time.LocalDate;
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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subcontract_supply_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontract_supply_schedulegen")
    @SequenceGenerator(
            name = "subcontract_supply_schedulegen",
            sequenceName = "subcontract_supply_scheduleseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "subcontract_supply_schedule_id")
    private Long id;


    // =========================
    // Header Details
    // =========================
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "sch_start_date")
    private LocalDate schStartDate;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "sch_end_date")
    private LocalDate schEndDate;


    // =========================
    // Party Details
    // =========================

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;


    // =========================
    // Contract / Job Order
    // =========================

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "job_order_no")
    private String jobOrderNo;


    // =========================
    // Prepared / Authorised
    // =========================

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @ManyToOne
    @JoinColumn(name = "authorised_by")
    private EmployeeMasterVO authorisedBy;


    // =========================
    // Remarks
    // =========================

    @Column(name = "remarks")
    private String remarks;


    // =========================
    // Common Fields
    // =========================

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "active")
    private boolean active ;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "screen_name")
    private String screenName = "SUB CONTRACT SUPPLIER SCHEDULE";

    @Column(name = "screen_code")
    private String screenCode = "SCSS";



    // =========================
    // Item Details
    // =========================

    @OneToMany(
            mappedBy = "subContractSupplyScheduleVO",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<SubContractSupplyScheduleItemDetailsVO> itemDetails;


    // =========================
    // JSON Display
    // =========================

    @JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }
    
    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}