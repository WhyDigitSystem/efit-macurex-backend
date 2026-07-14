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
@Table(name = "gateinwardentry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateInwardEntryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gateinwardentrygen")
	@SequenceGenerator(name = "gateinwardentrygen", sequenceName = "gateinwardentryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gateinwardentryid")
	private Long id;
	    
	@Column(name = "docid")
    private String docId ;
	
	@Column(name = "docdate")
    private LocalDate docDate =LocalDate.now();

	@Column(name = "suppliername")
	private String supplierName;

	@Column(name = "suppliercode")
	private String supplierCode;
	
	@Column(name = "ponumber")
	private String poNumber;

	@Column(name = "invoiceno")
	private String invoiceNo;

	@Column(name = "invoicedate")
	private LocalDate invoiceDate;

	@Column(name = "vehicleno")
	private String vehicleNo;

	@Column(name = "courierno")
	private String courierNo;

	@Column(name = "couriername")
	private String courierName;
	
	@Column(name = "narration")
	private String narration;
	
	@Column(name = "screencode", length = 5)
	private String screenCode ="GIN";

	@Column(name = "screenname", length = 25)
	private String screenName ="GATEINWARDENTRY";
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active=true ;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	
	@OneToMany(mappedBy = "gateInwardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<GateInwardEntryDetailsVO> gateInwardEntryDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
