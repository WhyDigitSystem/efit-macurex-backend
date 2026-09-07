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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_order_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderVO {

    // =========================
    // PRIMARY KEY
    // =========================

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "job_order_basicgen"
    )
    @SequenceGenerator(
        name = "job_order_basicgen",
        sequenceName = "job_order_basicseq",
        allocationSize = 1,
        initialValue = 1000000001
    )
    @Column(name = "job_order_basic_id")
    private Long id;


    // =========================
    // DOCUMENT DETAILS
    // =========================

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();


    // =========================
    // PLANT / BRANCH
    // =========================

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @Column(name = "belongs_to")
    private String belongsTo;


    // =========================
    // VENDOR DETAILS
    // =========================

    @ManyToOne
    @JoinColumn(name = "vendor")
    private CustomerVO vendor;

    @ManyToOne
    @JoinColumn(name = "gst_state")
    private GSTStateMasterVO gstState;


    // =========================
    // JOB ORDER FOR
    // =========================

    @Column(name = "job_order_for")
    private String jobOrderFor;


    // =========================
    // GST
    // =========================

    @Column(name = "is_igst_appl")
    private boolean isIgstAppl;


    // =========================
    // CONTRACT
    // =========================

    @Column(name = "contract_no")
    private String contractNo;


    // =========================
    // SERVICE DETAILS
    // =========================

    @ManyToOne
    @JoinColumn(name = "service_name")
    private ServiceAccMasterVO serviceName;

    @Column(name = "indent_time")
    private String indentTime;


    // =========================
    // TAX DETAILS
    // =========================

    @ManyToOne
    @JoinColumn(name = "hsn_sac_code")
    private HsnVO hsnSacCode;


    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage;


    // =========================
    // PAYMENT / DELIVERY
    // =========================

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate ;


    // =========================
    // AMOUNT
    // =========================

    @Column(name = "amount")
    private BigDecimal amount;


    // =========================
    // NARRATION / NOTE
    // =========================

    @Column(name = "narration")
    private String narration;

    @Column(name = "note")
    private String note;


    // =========================
    // COMMON FIELDS
    // =========================

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

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
    private String screenCode = "JO";

    @Column(name = "screen_name")
    private String screenName = "JOB ORDER";


    @OneToMany(
            mappedBy = "jobOrder",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<JobOrderDetailsVO> jobOrderDetails = new ArrayList<>();
    
    @OneToMany(
            mappedBy = "jobOrder",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<JobOrderTaxDetailsVO> jobOrderTaxDetails = new ArrayList<>();
	
    @OneToMany(
            mappedBy = "jobOrderVO",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<JobOrderAttachmentVO> attachments = new ArrayList<>();
    
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}