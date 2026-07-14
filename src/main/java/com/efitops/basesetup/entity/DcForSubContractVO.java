package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dcforsubcontract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DcForSubContractVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dcforsubcontractgen")
	@SequenceGenerator(name = "dcforsubcontractgen", sequenceName = "dcforsubcontractseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dcforsubcontractid")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate= LocalDate.now();
	@Column(name="scissueno")
	private String scIssueNo;
	@Column(name="customername")
	private String customerName;
	@Lob
	@Column(name = "customeraddress", columnDefinition = "LONGTEXT")
	private String customerAddress;
	@Column(name="routecardno")
	private String routeCardNo;
	@Column(name="gstno")
	private String gstNo;
	@Column(name="subcontractorname")
	private String subContractorName; 
	@Column(name="subcontractoraddress")
	private String subContractoraddress;
	@Column(name="subcontractorid")
	private String subContractorId;
	@Column(name="vehicleno")
	private String vehicleNo;
	@Column(name="duedate")
	private LocalDate Duedate;
	@Column(name="dispatchthrough")
	private String dispatchThrough;
	@Column(name="ewaybillno")
	private String ewayBillNo;
	@Column(name="narration")
	private String narration;
	
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode",length = 10)
	private String screenCode ="DCSC";
	@Column(name = "screenname",length = 30)
	private String screenName="DCFORSUBCONTRACT";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@OneToMany(mappedBy = "dcForSubContractVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<DcForSubContractDetailsVO> dcForSubContractDetailsVO;
	

}
