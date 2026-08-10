package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "sdvbasic")
@Data
public class SalesDeliveryScheduleVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sdvbasic_seq")
    @SequenceGenerator(name = "sdvbasic_seq",sequenceName = "sdvbasic_seq",allocationSize = 1)
    @Column(name = "sdvbasic_id")
    private Long id;

    @Column(name = "dlv_no")
    private String dlvNo;

    @Column(name = "dlv_date")
    private LocalDate dlvDate;

   
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchVO branch;

    @Column(name = "month_of_schedule")
    private String monthOfSchedule;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "month_year")
    private String monthYear;
    
    @Column(name = "remarks")
    private String remarks;

 
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerVO customer;
    
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

	@Column(name = "screen_code", length = 30)
	private String screenCode = "SALESDELIVERYSCHEDULE";
	@Column(name = "screen_name", length = 30)
	private String screenName = "SDS";
	

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
	
	@OneToMany(mappedBy = "salesDeliverySchedule",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true)
	@JsonManagedReference
	private List<SalesDeliveryScheduleDetailsVO> details = new ArrayList<>();

	
	
//	@OneToMany(mappedBy = "salesDeliverySchedule",
//	        cascade = CascadeType.ALL,
//	        orphanRemoval = true)
//	private List<SalesDeliverySchedulePlanVO> deliverySchedules = new ArrayList<>();


	

}