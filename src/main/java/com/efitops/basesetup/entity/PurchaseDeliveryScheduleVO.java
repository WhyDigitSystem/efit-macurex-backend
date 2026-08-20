package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_delivery_schedule_basic")
public class PurchaseDeliveryScheduleVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_delivery_schedule_basicgen")
    @SequenceGenerator(name = "purchase_delivery_schedule_basicgen", sequenceName = "purchase_delivery_schedule_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_delivery_schedule_basic_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;
    
    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "schedule_start_date")
    private LocalDate scheduleStartDate;

    @Column(name = "schedule_end_date")
    private LocalDate scheduleEndDate;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private CustomerVO supplier;

   
    @Column(name = "purchase_order_No")
    private String purchaseOrderNo;
   
    @Column(name = "purchase_order_date")
    private LocalDate purchaseOrderDate;
   

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
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @OneToMany(mappedBy = "purchaseDeliveryScheduleVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseDeliveryScheduleDetailsVO> purchaseDeliveryScheduleDetailsVO = new ArrayList<>();

    @Column(name = "screen_code", length = 10)
	private String screenCode = "PDS";
	@Column(name = "screen_name", length = 30)
	private String screenName = "Purchase Delivery Schedule";


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