package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inspection_requisition_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRequisitionNoteVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_requisition_notegen")
    @SequenceGenerator(
            name = "inspection_requisition_notegen",
            sequenceName = "inspection_requisition_noteseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "inspection_requisition_note_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "reason_for_inspection_request")
    private String reasonForInspectionRequest;

    
    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "request_comments")
    private String requestComments;

    @Column(name = "date")
    private LocalDate date ;

    @Column(name = "samples_submitted_to")
    private String samplesSubmittedTo;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "sample_quantity", precision = 15, scale = 5)
    private BigDecimal sampleQuantity;

    @Column(name = "product")
    private String product;

    @Column(name = "customer")
    private String customer;

    @Column(name = "supplier")
    private String supplier;

    @ManyToOne
    @JoinColumn(name = "purchase_manager")
    private EmployeeMasterVO purchaseManager;

    @Column(name = "purchase_manager_date")
    private LocalDate purchaseManagerDate;

    @ManyToOne
    @JoinColumn(name = "tdc_manager")
    private EmployeeMasterVO tdcManager;

    @Column(name = "tdc_manager_date")
    private LocalDate tdcManagerDate;

    @ManyToOne
    @JoinColumn(name = "quality_manager")
    private EmployeeMasterVO qualityManager;

    @Column(name = "quality_manager_date")
    private LocalDate qualityManagerDate;

    @ManyToOne
    @JoinColumn(name = "production_manager")
    private EmployeeMasterVO productionManager;

    @Column(name = "production_manager_date")
    private LocalDate productionManagerDate;

    @ManyToOne
    @JoinColumn(name = "approval_requested_by")
    private EmployeeMasterVO approvalRequestedBy;

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
    private String screenName = "INSPECTION REQUISITION NOTE";

    @Column(name = "screen_code")
    private String screenCode = "IRN";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}