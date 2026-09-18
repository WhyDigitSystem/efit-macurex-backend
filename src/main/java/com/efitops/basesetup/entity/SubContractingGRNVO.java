package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "sub_contracting_grn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_contracting_grngen")
    @SequenceGenerator(
            name = "sub_contracting_grngen",
            sequenceName = "sub_contracting_grnseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "sub_contracting_grn_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "belongs_to")
    private String belongsTo;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @ManyToOne
    @JoinColumn(name = "vendor")
    private CustomerVO vendor;

    @ManyToOne
    @JoinColumn(name = "vendor_location")
    private LocationVO vendorLocation;

    @Column(name = "gst_state")
    private String gstState;

    @Column(name = "gate_pass_no")
    private String gatePassNo;

    @Column(name = "is_igst_appl")
    private Boolean isIGSTAppl;

    @Column(name = "schedule_no")
    private String scheduleNo;

    @Column(name = "gstn_no")
    private String gstnNo;

    @Column(name = "rework")
    private String rework;

    @Column(name = "gst_type")
    private String gstType;

    @Column(name = "is_revs_chrg")
    private boolean isRevsChrg;

    @Column(name = "sch_start_date")
    private LocalDate schStartDate;

    @ManyToOne
    @JoinColumn(name = "service_name")
    private ServiceAccMasterVO serviceName;

    @Column(name = "sch_end_date")
    private LocalDate schEndDate;

    @ManyToOne
    @JoinColumn(name = "sac_code")
    private HsnVO sacCode;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "supplier_dc_no")
    private String supplierDcNo;

    @Column(name = "tax_percentage", precision = 15, scale = 5)
    private BigDecimal taxPercentage;

    @Column(name = "supplier_dc_date")
    private LocalDate supplierDcDate;

    @Column(name = "grn_clear_time")
    private String grnClearTime;

    @Column(name = "basic_amount", precision = 15, scale = 5)
    private BigDecimal basicAmount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "total_tax", precision = 15, scale = 5)
    private BigDecimal totalTax;

    @Column(name = "total_amount", precision = 15, scale = 5)
    private BigDecimal totalAmount;

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
    private String screenName = "SUB CONTRACTING GRN";

    @Column(name = "screen_code")
    private String screenCode = "SCGRN";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "subContractingGRNVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<SubContractingGRNDetailsVO> details = new ArrayList<>();

    @OneToMany(
            mappedBy = "subContractingGRNVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<SubContractingGRNTaxDetailsVO> taxDetails = new ArrayList<>();

    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}