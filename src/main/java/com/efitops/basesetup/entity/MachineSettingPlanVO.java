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
@Table(name = "machine_setting_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineSettingPlanVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_setting_plangen")
    @SequenceGenerator(
            name = "machine_setting_plangen",
            sequenceName = "machine_setting_planseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "machine_setting_plan_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    // Plant Id
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    // Doc No.
    @Column(name = "doc_id")
    private String docId;

    // Date
    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    // Item
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    // Operation No.
    @Column(name = "operation_no")
    private String operationNo;

    // Operation Name
    @Column(name = "operation_name")
    private String operationName;

    // Process Sheet No
    @Column(name = "process_sheet_no")
    private String processSheetNo;

    // Machine No.
    @Column(name = "machine_no")
    private String machineNo;

    // Machine Name
    @Column(name = "machine_name")
    private String machineName;

    // Make
    @Column(name = "make")
    private String make;

    // Tool Replacement Plan
    @Column(name = "tool_replacement_plan")
    private String toolReplacementPlan;

    // Prepared By
    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    // Approved By
    @ManyToOne
    @JoinColumn(name = "approved_by")
    private EmployeeMasterVO approvedBy;

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
    private String screenName = "MACHINE SETTING PLAN";

    @Column(name = "screen_code")
    private String screenCode = "MSP";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "machineSettingPlanVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<MachineSettingPlanDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
