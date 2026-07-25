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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentgen")
	@SequenceGenerator(name = "departmentgen", sequenceName = "departmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "departmentid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
//	@Column(name = "docdate")
//	private LocalDate docDate = LocalDate.now();
	@Column(name = "doc_id")
	private String docId;
	@Column(name = "department_code")
	private String departmentCode;
	@Column(name = "departmen_tname")
	private String departmentName;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by", length = 25)
	private String createdBy;
	@Column(name = "modify_by", length = 25)
	private String updatedBy;
	@Column(name = "cancel_remarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
    
	@Column(name = "financial_year", length = 5)
    private String finYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "DEPT";
	@Column(name = "screenname", length = 30)
	private String screenName = "DEPARTMENT";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
