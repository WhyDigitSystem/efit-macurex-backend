package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sampleapproval")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleApprovalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sampleapprovalgen")
	@SequenceGenerator(name = "sampleapprovalgen", sequenceName = "sampleapprovalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sampleapprovalid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "drgno")
	private String drgNo;
	@Column(name = "operation")
	private String operation;
	@Column(name = "cycletime")
	private int cycleTime;
	@Column(name = "machineno")
	private String machineNo;
	@Column(name = "machinename")
	private String machineName;
	@Column(name = "joborderno")
	private String jobOrderNo;
	@Column(name = "sfift")
	private String shift;
	@Column(name = "shiftdate")
	private LocalDate shiftDate;
	@Column(name = "shifttime")
	private LocalDateTime shiftTime;
	@Column(name = "sampleqty")
	private int sampleQty;
	@Column(name = "docformatno")
	private String docFormatNo;
	
	//SUMMARY
	@Column(name = "generalremarks")
	private String generalRemarks;
	@Column(name = "operatorname")
	private String operatorName;
	@Column(name = "shiftincharge")
	private String shiftInCharge;
	@Column(name = "qualityname")
	private String qualityName;
	@Column(name = "narration")
	private String narration;
	
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear	",length =10)
	private String finYear;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "screencode")
	private String screenCode = "SAMP";
	@Column(name = "screenname")
	private String screenName = "SAMPLE APPROVAL";
	
	
	@OneToMany(mappedBy = "sampleApprovalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SampleApprovalDetailsVO> sampleApprovalDetailsVO;
	
	@OneToMany(mappedBy = "sampleApprovalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SampleApprovalAttachmentVO> documents;

	

	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
