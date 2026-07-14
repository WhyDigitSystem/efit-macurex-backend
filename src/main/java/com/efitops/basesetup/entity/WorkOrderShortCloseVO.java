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

import org.springframework.format.annotation.DateTimeFormat;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workordershortclose")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderShortCloseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workordershortclosegen")
	@SequenceGenerator(name = "workordershortclosegen", sequenceName = "workordershortcloseseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "workordershortcloseid", columnDefinition = "BIGINT DEFAULT 0")
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
	@Column(name = "workordernumber")
	private String workOrderNumber;
	@Column(name = "shortclosedate")
	private LocalDate shortCloseDate;
	@Column(name = "currency")
	private String currency;
	@Column(name = "productionmgr")
	private String productionMgr;
	@Column(name = "status")
	private String status;
//	@Column(name = "customerspecialrequirement")
//	private String customerSpecialRequirement;
	
	@Column(name = "approvestatus", length = 20)
	private String approveStatus="Pending";
	@Column(name = "approveby", length = 20)
	private String approveBy;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	@Column(name = "approveon")
	private String approveOn;

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
	private String screenCode = "WOSC";
	@Column(name = "screenname", length = 30)
	private String screenName = "WORKORDERSHORTCLOSE";

	@OneToMany(mappedBy = "workOrderShortCloseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<WorkOrderShortCloseDetailsVO> workOrderShortCloseDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
