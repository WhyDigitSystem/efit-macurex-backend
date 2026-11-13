package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_short_close")
public class PurchaseShortCloseVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosegen")
    @SequenceGenerator(name = "purchaseshortclosegen", sequenceName = "purchaseshortcloseseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchaseshortclose_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchVO plant;

    @Column(name = "short_close_no")
    private String shortCloseNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "short_close_date")
    private LocalDate shortCloseDate;

    @Column(name = "type")
    private String type;

    // Supplier Code / Supplier Name both resolve from the same Party/Customer master record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_purchase_order_id")
    private LocalPurchaseOrderVO localPurchaseOrder;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;


    @Column(name = "reference_for_short_close", length = 1000)
    private String referenceForShortClose;

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

    @OneToMany(mappedBy = "purchaseShortCloseVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseShortCloseDetailsVO> purchaseShortCloseDetailsVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}