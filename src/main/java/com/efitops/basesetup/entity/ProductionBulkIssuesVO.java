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
@Table(name = "production_bulk_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssuesVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_bulk_issuesgen")
    @SequenceGenerator(
            name = "production_bulk_issuesgen",
            sequenceName = "production_bulk_issuesseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "production_bulk_issues_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "fg_item")
    private ItemMasterVO fgItem;

    @Column(name = "type")
    private String type;

    @Column(name = "indent_no")
    private String indentNo;

    @Column(name = "purchase_material_ref")
    private String purchaseMaterialRef;

    @Column(name = "ref_no")
    private String refNo;

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location")
    private LocationVO toLocation;

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
    private String screenName = "PRODUCTION (BULK) ISSUES";

    @Column(name = "screen_code")
    private String screenCode = "PBI";

    @Column(name = "org_id")
    
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "productionBulkIssuesVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<ProductionBulkIssuesDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}