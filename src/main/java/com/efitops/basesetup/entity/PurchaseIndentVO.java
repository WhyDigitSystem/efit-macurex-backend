package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "Indent_Basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Indent_Basicgen")
    @SequenceGenerator(name = "Indent_Basicgen", sequenceName = "Indent_Basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "Indent_Basic_id")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @ManyToOne
   	@JoinColumn(name = "branch")
   	private BranchVO branch;

   
    
    @Column(name = "belongs_to")
    private String belongsTo;


    @Column(name = "doc_date")
    private LocalDate docDate;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @ManyToOne
    @JoinColumn(name = "by_whom")
    private EmployeeMasterVO byWhom;

    @Column(name = "approved")
    private boolean approved;

    // Indent Summary - kept directly on parent, no child table
    @Column(name = "remarks")
    private String remarks;

    @Column(name = "org_id")
    private Long orgId;

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

    @Column(name = "screen_code")
    private String screenCode = "PI";

    @Column(name = "screen_name")
    private String screenName = "PURCHASEINDENT";

    @OneToMany(mappedBy = "purchaseIndentVO", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseIndentDetailsVO> details = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseIndentVO", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseIndentAttachmentVO> attachments = new ArrayList<>();

    @JsonGetter("active")
    public String getActiveStr() {
        return active ? "Active" : "In-Active";
    }

    @JsonGetter("cancel")
    public String getCancelStr() {
        return cancel ? "T" : "F";
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}