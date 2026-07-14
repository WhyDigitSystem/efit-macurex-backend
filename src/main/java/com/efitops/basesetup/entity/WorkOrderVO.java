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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workorder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workordergen")
	@SequenceGenerator(name = "workordergen", sequenceName = "workorderseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "workorderid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "customerpono")
	private String customerPoNo;
	@Column(name = "quotationno")
	private String quotationNo;
	@Column(name = "quotationid")
	private Long quotationId;
	@Column(name = "currency")
	private String currency;
	@Column(name = "customerduedate")
	private LocalDate customerDueDate;
	@Column(name = "duedate")
	private LocalDate dueDate;
	@Column(name = "productionmgr")
	private String productionMgr;
	@Column(name = "customerspecialrequirement")
	private String customerSpecialRequirement;
	@Column(name = "status", length = 30)
	private String status = "PENDING";

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "screencode", length = 30)
	private String screenCode = "WO";
	@Column(name = "screenname", length = 30)
	private String screenName = "WORK ORDER";

	@OneToMany(mappedBy = "workOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<WorkOrderDetailsVO> workOrderDetailsVO;

	@OneToMany(mappedBy = "workOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<WorkOrderTermsVO> workOrderTermsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
