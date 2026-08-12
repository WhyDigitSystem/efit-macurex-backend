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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customercomplaintmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerComplaintEntryVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customercomplaintmastergen")
	@SequenceGenerator(name = "customercomplaintmastergen", sequenceName = "customercomplaintmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customercomplaintmaster_id")
	private Long id;
	
	@Column(name = "qty_no")
	private Long qtyNo;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
//	@ManyToOne
//	@JoinColumn(name = "customer_name")
//	private CustomerVO customerName;
	
	@Column(name = "buyer_name")
	private String buyerName;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "details_of_complaint")
	private String detailsOfComplaint;
	
	@Column(name = "prepared_by")
	private String preparedBy;
	
	@Column(name = "user_category")
	private String userCategory;
	
	@Column(name = "financial_year")
	private String financialYear;
	
	@Column(name = "prefix")
	private String prefix;
	
	@Column(name = "complaint_no")
	private String complaintNo;
	
	@Column(name = "complaint_date")
	private LocalDate complaintDate;
	
	@Column(name = "complaint_type")
	private String complaintType;
	
	@Column(name = "customer_ref_no")
	private String customerRefNo;
	

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updated_By;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	
	
	
   
	@Column(name = "screen_code",length = 10)
	private String screenCode ="CCE";
	@Column(name = "screen_name",length = 30)
	private String screenName="Customer Complaint Entry";
	
	

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	

	
	

}
