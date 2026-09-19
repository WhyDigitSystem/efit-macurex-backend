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
@Table(name = "bom_correction_request_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomCorrectionRequestNoteVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bom_correction_request_notegen")
    @SequenceGenerator(
            name = "bom_correction_request_notegen",
            sequenceName = "bom_correction_request_noteseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "bom_correction_request_note_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @ManyToOne
    @JoinColumn(name = "correction_requested_by")
    private EmployeeMasterVO correctionRequestedBy;
    
    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "correction_request_approved_by")
    private EmployeeMasterVO correctionRequestApprovedBy;

    @ManyToOne
    @JoinColumn(name = "fg_part_no")
    private ItemMasterVO fgPartNo;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "customer_part_no")
    private String customerPartNo;

    @Column(name = "customer_name")
    private String customerName;

    
    @Column(name = "supplier")
    private String supplier;

    @Column(name = "reason_for_change")
    private String reasonForChange;

    @ManyToOne
    @JoinColumn(name = "manager_production")
    private EmployeeMasterVO managerProduction;

    @ManyToOne
    @JoinColumn(name = "manager_quality")
    private EmployeeMasterVO managerQuality;

    @ManyToOne
    @JoinColumn(name = "manager_tdc")
    private EmployeeMasterVO managerTdc;

    @ManyToOne
    @JoinColumn(name = "manager_purchase")
    private EmployeeMasterVO managerPurchase;

    @ManyToOne
    @JoinColumn(name = "authorised_signator")
    private EmployeeMasterVO authorisedSignator;

    @Column(name = "decision")
    private String decision;

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
    private String screenName = "BOM CORRECTION REQUEST NOTE";

    @Column(name = "screen_code")
    private String screenCode = "BCRN";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "bomCorrectionRequestNoteVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<BomCorrectionRequestNoteDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}