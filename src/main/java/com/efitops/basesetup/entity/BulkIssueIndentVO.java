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
@Table(name = "bulk_issue_indent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulk_issue_indentgen")
    @SequenceGenerator(
            name = "bulk_issue_indentgen",
            sequenceName = "bulk_issue_indentseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "bulk_issue_indent_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @Column(name = "belongs_to")
    private String belongsTo;

    @ManyToOne
    @JoinColumn(name = "fg_sfg_item")
    private ItemMasterVO fgSfgItem;

    @ManyToOne
    @JoinColumn(name = "bom_id")
    private BillOfMaterialVO bom;

    @Column(name = "time_of_indent")
    private String timeOfIndent;

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @Column(name = "approved_by_pm")
    private String approvedByPM;

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @ManyToOne
    @JoinColumn(name = "authorised_by")
    private EmployeeMasterVO authorisedBy;

    @Column(name = "remarks")
    private String remarks;

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
    private String screenName = "BULK ISSUE INDENT";

    @Column(name = "screen_code")
    private String screenCode = "BII";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "bulkIssueIndentVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<BulkIssueIndentDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}