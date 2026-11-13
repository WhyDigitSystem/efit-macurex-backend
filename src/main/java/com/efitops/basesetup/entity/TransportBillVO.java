package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transport_bill")
public class TransportBillVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transportbillgen")
    @SequenceGenerator(
            name = "transportbillgen",
            sequenceName = "transportbillseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "transportbill_id")
    private Long id;

    // Plant ID is linked to Branch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch")
    private BranchVO branch;

    // Doc. No is linked to Document Type Master, docNo is the generated/entered number
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentTypeMasterVO documentType;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate=LocalDate.now();

    // Transport Name is linked to Transport Master
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private TransportMasterVO transportName;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "bill_received_date")
    private LocalDate billReceivedDate;

    @Column(name = "acc_received_date")
    private LocalDate accReceivedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private EmployeeMasterVO receivedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_received_by")
    private EmployeeMasterVO accReceivedBy;
    
    @Column(name = "org_id")
    private Long orgId;
    @Column(name = "active")
    private boolean active;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long updatedBy;
    @Column(name = "cancel")
    private boolean cancel=false;
    @Column(name = "cancel_remarks")
    private String cancelRemarks;

	@Column(name = "financial_year", length = 5)
	private String financialYear;
	@Column(name = "screen_code", length = 30)
	private String screenCode = "TB";
	@Column(name = "screen_name", length = 30)
	private String screenName = "TRANSPORTBILL";
	
    @OneToMany(mappedBy = "transportBillVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TransportBillPaymentDetailsVO> paymentDetails1 = new ArrayList<>();

//    @OneToMany(mappedBy = "transportBillVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<TransportBillPaymentDetails2VO> paymentDetails2 = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}