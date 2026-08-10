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
@Table(name = "stock_transfer_chellan_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stocktransferchellanbasicgen")
	@SequenceGenerator(name = "stocktransferchellanbasicgen", sequenceName = "stocktransferchellanbasicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_chellan_basic_id")
    private Long id;
	
	@Column(name = "doc_id")
	private String docID;
	
	@Column(name = "transfer_date")
	private LocalDate transferDate;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	
	@ManyToOne
	@JoinColumn(name = "listofvalues_id")
	private ListOfValuesVO listOfValues;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;
	
	@Column(name = "stock_posting")
	private String stockPosting;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "no_of_packages")
	private int noOfPackages;
	
	@Column(name = "other_packages")
	private int otherPackages;
	
	@Column(name = "import_local")
	private String importLocal;
	
	
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
	private String screenCode ="STC";
	@Column(name = "screen_name",length = 30)
	private String screenName="Stock Tranfer Challan";
	
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
 
}
