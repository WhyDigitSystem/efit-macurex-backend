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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "process_validation_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessValidationEntryVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_validation_entrygen")
    @SequenceGenerator(
            name = "process_validation_entrygen",
            sequenceName = "process_validation_entryseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "process_validation_entry_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;


    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;


    @ManyToOne
    @JoinColumn(name = "process_sheet_no")
    private ProcessSheetCompRoutingVO processSheetNo;

    @Column(name = "validation_reason")
    private String validationReason;

    @Column(name = "details_of_changes")
    private String detailsOfChanges;

    @ManyToOne
    @JoinColumn(name = "control_plan")
    private ControlPlanVO controlPlan;

    @Column(name = "characteristics_to_be_measured")
    private String characteristicsToBeMeasured;

    @Column(name = "specification")
    private String specification;

    @Column(name = "date_implemented")
    private LocalDate dateImplemented;

    @Column(name = "recommended_for_production")
    private String recommendedForProduction;

    @Column(name = "date_of_next_validation")
    private LocalDate dateOfNextValidation;

    @Column(name = "results_remarks")
    private String resultsRemarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_name")
    private String screenName = "PROCESS VALIDATION ENTRY";

    @Column(name = "screen_code")
    private String screenCode = "PVE";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @OneToMany(
            mappedBy = "processValidationEntryVO",
            cascade = CascadeType.ALL
    )
    private List<ProcessValidationEntryDetailsVO> details =
            new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}