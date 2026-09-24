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
@Table(name = "production_bulk_issue_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_bulk_issue_basicgen")
    @SequenceGenerator(name = "production_bulk_issue_basicgen", sequenceName = "production_bulk_issue_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "production_bulk_issue_basic_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId; 


    @Column(name = "belongs_to")
    private String belongsTo; 
    
    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now(); 

    @ManyToOne
    @JoinColumn(name = "fg_item")
    private ItemMasterVO fgItem; 

    @Column(name = "indent_no")
    private String indentNo;

    @Column(name = "issue_date")
    private LocalDate issueDate; 

    @Column(name = "purchase_material_ref")
    private String purchaseMaterialRef; 

    @Column(name = "type")
    private String type; // Type (e.g., Regular)

    @Column(name = "ref_no")
    private String refNo; // Ref. No

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation; // From. Location

    @ManyToOne
    @JoinColumn(name = "to_location")
    private LocationVO toLocation; // To Location

    @Column(name = "remarks")
    private String remarks; // Summary - Remarks

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel ;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_name")
    private String screenName = "ProductionBulkIssues";

    @Column(name = "screen_code")
    private String screenCode = "PBI";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @OneToMany(mappedBy = "productionBulkIssueVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductionBulkIssueDetailsVO> ProductionBulkIssueDetailsVO;

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