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
@Table(name = "job_order_amendment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderAmendmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_order_amendment_gen")
    @SequenceGenerator(
            name = "job_order_amendment_gen",
            sequenceName = "job_order_amendmentseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "job_order_amendment_id")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;
    
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

    @Column(name = "job_order_no")
    private String jobOrderNo;

    @Column(name = "job_order_date")
    private LocalDate jobOrderDate;

    @Column(name = "revision_no")
    private String revisionNo;

    @Column(name = "old_delivery_date")
    private LocalDate oldDeliveryDate;

    @Column(name = "new_delivery_date")
    private LocalDate newDeliveryDate;

    @Column(name = "remarks")
    private String remarks;

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
    private String screenCode = "JOA";

    @Column(name = "screen_name")
    private String screenName = "JOB ORDER AMENDMENT";

    @OneToMany(mappedBy = "jobOrderAmendment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<JobOrderAmendmentDetailsVO> jobOrderAmendmentDetails;


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
