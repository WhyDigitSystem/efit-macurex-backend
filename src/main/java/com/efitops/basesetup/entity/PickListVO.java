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
@Table(name = "picklist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picklistgen")
	@SequenceGenerator(name = "picklistgen", sequenceName = "picklistseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "picklistid")
	private Long id;
	
	@Column(name ="docid")
    private String docId;

    @Column(name = "docdate")
    private LocalDate docDate=LocalDate.now();
    
	@Column(name = "customername")
	private String customerName;
	
	@Column(name = "customercode")
	private String customerCode;

	@Column(name = "routecardno")
	private String routeCardNo;

	@Column(name = "workorderno")
	private String workOrderNo;

	@Column(name = "itemissuetoproductionno")
	private String itemIssueToProductionNo;
	
	@Column(name = "department")
	private String department;

	@Column(name = "location")
	private String location;

	@Column(name = "shift")
	private String shift;

	@Column(name = "pickedby")
	private String pickedBy;
	
	@Column(name = "fgpartno")
	private String fgPartNo;
	@Column(name = "remarks")
	private String remarks;
	@Column(name="status")
	private String status;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;

	@Column(name = "screencode", length = 5)
	private String screenCode = "PL";
	@Column(name = "screenname", length = 30)
	private String screenName = "PICKLIST";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@OneToMany(mappedBy = "pickListVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PickListDetailsVO> pickListDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
