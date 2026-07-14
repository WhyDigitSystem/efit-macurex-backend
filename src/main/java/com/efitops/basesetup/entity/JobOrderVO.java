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
@Table(name = "joborder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobordergen")
	@SequenceGenerator(name = "jobordergen", sequenceName = "joborderseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "joborderid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

//	Main Fields
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
    private LocalDate docDate=LocalDate.now();
	@Column(name = "shift")
	private String shift;
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "workorderno")
	private String workOrderNo;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customerpono")
	private String customerPoNo;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "operationname")
	private String operationName;
	@Column(name = "cycletimeinsecs")
	private String cycleTimeInSecs;
	@Column(name = "normshr")
	private String normsHr;
	@Column(name = "status")
	private String status;
	@Column(name = "operatorname")
	private String operatorName;
	@Column(name = "productionqty")
	private int productionQty;
//	Defaul Fields
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "createdby", length = 30)
	private String createdBy;
	@Column(name = "modifiedby", length = 30)
	private String modifiedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "JO";
	@Column(name = "screenname", length = 30)
	private String screenName = "JOB ORDER";
//	Summary Fields
	@Column(name = "narration")
	private String narration;

	@OneToMany(mappedBy = "jobOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<JobOrderDetailsVO> jobOrderDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

}
