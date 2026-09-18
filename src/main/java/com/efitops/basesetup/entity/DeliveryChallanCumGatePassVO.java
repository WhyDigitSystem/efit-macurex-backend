package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "delivery_challan_cum_gate_pass")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCumGatePassVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_challan_cum_gate_passgen")
    @SequenceGenerator(
            name = "delivery_challan_cum_gate_passgen",
            sequenceName = "delivery_challan_cum_gate_passseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "delivery_challan_cum_gate_pass_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "type")
    private String type;

    @Column(name = "is_igst_appl")
    private boolean isIGSTAppl;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @Column(name = "gstn_no")
    private String gstnNo;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;

    @ManyToOne
    @JoinColumn(name = "to_branch")
    private BranchVO toBranch;

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @Column(name = "mode_of_transport")
    private String modeOfTransport;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @ManyToOne
    @JoinColumn(name = "work_order_no")
    private JobOrderVO workOrderNo;

    @Column(name = "total_qty", precision = 10, scale = 2)
    private BigDecimal totalQty;

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @Column(name = "remarks")
    private String remarks;


    // Common Fields

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
    private String screenName = "DELIVERY CHALLAN CUM GATE PASS";

    @Column(name = "screen_code")
    private String screenCode = "DCGP";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;


    @OneToMany(mappedBy = "deliveryChallanCumGatePassVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DeliveryChallanCumGatePassDetailsVO> deliveryChallanCumGatePassDetailsVO;


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