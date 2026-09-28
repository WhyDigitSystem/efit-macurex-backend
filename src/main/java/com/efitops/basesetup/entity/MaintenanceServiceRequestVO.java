package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maintenance_service_request")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MaintenanceServiceRequestVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenance_service_requestgen")
	@SequenceGenerator(name = "maintenance_service_requestgen", sequenceName = "maintenance_service_requestseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "maintenance_service_request_id")
	private Long id;
	
	
	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
    @ManyToOne
    @JoinColumn(name = "belong_to")
    private ListOfValuesDetailsVO belongTo;
 
    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @Column(name = "mail_id")
    private String mailId;

    @Column(name = "reported_time")
    private String reportedTime;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "completed")
    private String completed;

    @ManyToOne
    @JoinColumn(name = "priority")
    private ListOfValuesDetailsVO priority;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private EmployeeMasterVO requestedBy;

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "service_required")
    private String serviceRequired;

    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "active")
	private boolean active;
	   
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "MAINTENANCESERVICEREQUEST";
	@Column(name = "screen_code")
    private String screenCode = "MSR";
		

	@JsonGetter("activeStatus")
	public String getActiveStatus() {
	return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
	return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
