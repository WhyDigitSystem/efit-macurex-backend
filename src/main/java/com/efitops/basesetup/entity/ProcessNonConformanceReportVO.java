package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "processnonconformancereport")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNonConformanceReportVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processnonconformancereportgen")
	@SequenceGenerator(name = "processnonconformancereportgen", sequenceName = "processnonconformancereportseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "processnonconformancereportid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "qtyavailable", precision = 10, scale = 2)
	private BigDecimal qtyAvailable;
	@Column(name = "qtydefective")
	private BigDecimal qtyDefective;
	@Column(name = "briefdescription")
	private String briefdescription;
	@Column(name = "rootcause")
	private String rootCause;
	@Column(name = "disposition")
	private String disPosition;
	@Column(name = "process")
	private String process;
	@Column(name = "responsibility")
	private String responsibility;
	@Column(name = "correctiveaction")
	private String correctiveAction;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "verify")
	private String verify;
	@Column(name = "adequacy")
	private String adequacy;
	@Column(name = "created")
	private String created;
	@Column(name = "targetdate")
	private LocalDate targetDate;
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "actualdateofcompletion")
	private LocalDate actualDateOfCompletion;
	@Column(name = "effectivenessofcorrective")
	private String effectivenessOfCorrective;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "parttype")
	private String partType;
	@Column(name = "documentformateno")
	private String documentFormateNo;
	@Column(name = "signature")
	private String signature;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branch")
	private String branch;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks")
	private String cancelRemarks;

	@Column(name = "finyear")
	private String finYear;

	@Column(name = "screencode", length = 5)
	private String screenCode = "PNCR";

	@Column(name = "screenname", length = 25)
	private String screenName = "PROCESSNONCONFORMANCEREPORT";

	@OneToMany(mappedBy = "processNonConformanceReportVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProcessNonConformanceReportAttachmentVO> documents;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
