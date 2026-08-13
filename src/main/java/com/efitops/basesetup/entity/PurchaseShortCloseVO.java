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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_short_close")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseShortCloseVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosegen")
    @SequenceGenerator(
            name = "purchaseshortclosegen",
            sequenceName = "purchaseshortcloseseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchaseshortclose_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "short_close_no")
    private String shortCloseNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "short_close_date")
    private LocalDate shortCloseDate;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne
    @JoinColumn(name = "local_purchase_order_id")
    private LocalPurchaseOrderVO localPurchaseOrder;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "reference_for_short_close", length = 1000)
    private String referenceForShortClose;

    // ---------------- Audit / Organization ----------------

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_by")
    private Long updatedBy;

    // ---------------- Children ----------------

    @OneToMany(
            mappedBy = "purchaseShortCloseVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseShortCloseDetailsVO> purchaseShortCloseDetailsVO =
            new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    @Builder.Default
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}