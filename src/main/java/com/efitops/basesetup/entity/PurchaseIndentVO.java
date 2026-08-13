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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseindent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseIndentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseindentgen")
    @SequenceGenerator(
            name = "purchaseindentgen",
            sequenceName = "purchaseindentseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchaseindent_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "indent_no", length = 30)
    private String indentNo;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @ManyToOne
    @JoinColumn(name = "belongs_to")
    private ListOfValuesDetailsVO belongsTo;

    @Column(name = "indent_date")
    private LocalDate indentDate;

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

    @Column(name = "remarks", length = 1000)
    private String remarks;

    // ---------------- Audit / Organization ----------------

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "created_by", length = 30)
    private String createdBy;

    @Column(name = "modified_by", length = 30)
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks", length = 150)
    private String cancelRemarks;

    @Column(name = "screen_code", length = 10)
    @Builder.Default
    private String screenCode = "PI";

    @Column(name = "screen_name", length = 30)
    @Builder.Default
    private String screenName = "PURCHASEINDENT";

    // ---------------- Children ----------------

    @OneToMany(
            mappedBy = "purchaseIndentVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseIndentDetailsVO> details = new ArrayList<>();

    @OneToMany(
            mappedBy = "purchaseIndentVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
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
    @Builder.Default
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}