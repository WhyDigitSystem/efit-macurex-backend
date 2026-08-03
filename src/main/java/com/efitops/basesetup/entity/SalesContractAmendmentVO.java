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
@Table(name = "sale_contract_amendment_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesContractAmendmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salecontractamendmentbasicgen")
	@SequenceGenerator(name = "salecontractamendmentbasicgen", sequenceName = "salecontractamendmentbasicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sale_contract_amendment_basic_id")
	private Long id;
	
	@Column(name = "contract_amd_no")
	private String contractAmdNo;
	
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "contract_date")
	private String contractDate;
	
	@Column(name = "cust_po_no")
	private String custPoNo;
	
	@Column(name = "cust_po_date" )
	private String custPoDate;
	
	@Column(name = "revision_no")
	private String revisionNo;
	
	
	@Column(name = "remarks")
	private String remarks;
	
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
	private String screenCode ="SCA";
	@Column(name = "screen_name",length = 30)
	private String screenName="Sales Contract Amendment";
	
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
	
	@OneToMany(mappedBy = "salesContractAmendmentVO",
	        cascade = CascadeType.ALL
	    )
	@JsonManagedReference
	private List<SalesContractAmdDetailsVO> salesContractAmdDetailsVO;

}
