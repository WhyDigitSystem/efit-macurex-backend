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
@Table(name = "purchase_delivery_schedule")
public class PurchaseDeliveryScheduleVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasedeliveryschedulegen")
    @SequenceGenerator(name = "purchasedeliveryschedulegen", sequenceName = "purchasedeliveryscheduleseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasedeliveryschedule_id")
    private Long id;

    // Plant ID -> Branch (same pattern as Purchase Contract's "plant")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchVO plant;

    // Belongs To -> plain text, independent of the Plant/Branch FK above
    @Column(name = "belongs_to")
    private String belongsTo;

    // Doc No -> auto generated (prefix + running number), same pattern as Contract No
    @Column(name = "doc_no")
    private String docNo;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "sch_start_date")
    private LocalDate schStartDate;

    @Column(name = "sch_end_date")
    private LocalDate schEndDate;

    // Supplier Code / Supplier Name -> both come from the same Party/Customer master record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    // Which source poId refers to: "PURCHASE_CONTRACT" (live) or "LOCAL_PURCHASE_ORDER" (not wired up yet)
    @Column(name = "po_type")
    private String poType;


    // id of the row in the source table named by poType above
    @Column(name = "po_id")
    private Long poId;

    // snapshot of the PO's number/date at selection time, so this record doesn't
    // silently change if the source PO is edited later
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_purchase_order_id")
    private LocalPurchaseOrderVO localPurchaseOrder;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "note")
    private String note;

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

    @OneToMany(mappedBy = "purchaseDeliveryScheduleVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseDeliveryScheduleDetailsVO> purchaseDeliveryScheduleDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseDeliveryScheduleVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseDeliveryScheduleLineVO> purchaseDeliveryScheduleLineVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}