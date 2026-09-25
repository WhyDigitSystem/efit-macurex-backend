package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "inprocess_inspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inprocess_inspectiongen")
    @SequenceGenerator(
            name = "inprocess_inspectiongen",
            sequenceName = "inprocess_inspectionseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "inprocess_inspection_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @ManyToOne
    @JoinColumn(name = "control_plan")
    private ControlPlanVO controlPlan;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;

    @ManyToOne
    @JoinColumn(name = "operation_no")
    private OperationMasterVO operationNo;

    @Column(name = "specification")
    private String specification;

    @ManyToOne
    @JoinColumn(name = "part_no")
    private ItemMasterVO partNo;
    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @Column(name = "stock", precision = 15, scale = 5)
    private BigDecimal stock;

    @ManyToOne
    @JoinColumn(name = "machine_no")
    private MachineMasterVO machineNo;

    @ManyToOne
    @JoinColumn(name = "shift")
    private ShiftVO shift;

    @Column(name = "produced_qty", precision = 15, scale = 5)
    private BigDecimal producedQty;

    @Column(name = "accepted_qty", precision = 15, scale = 5)
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty", precision = 15, scale = 5)
    private BigDecimal rejectedQty;

    @Column(name = "rew_qty", precision = 15, scale = 5)
    private BigDecimal rewQty;

    @ManyToOne
    @JoinColumn(name = "inspected_by")
    private EmployeeMasterVO inspectedBy;

    @ManyToOne
    @JoinColumn(name = "verified_by")
    private EmployeeMasterVO verifiedBy;

    @ManyToOne
    @JoinColumn(name = "to_location")
    private LocationVO toLocation;

    @Column(name = "nc_detail")
    private String ncDetail;

    @Column(name = "action_taken")
    private String actionTaken;

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
    private String screenName = "INPROCESS INSPECTION";

    @Column(name = "screen_code")
    private String screenCode = "IPI";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "inprocessInspectionVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<InprocessInspectionDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
