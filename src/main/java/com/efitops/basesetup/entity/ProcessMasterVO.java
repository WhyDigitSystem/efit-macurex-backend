package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processmastergen")
	@SequenceGenerator(name = "processmastergen", sequenceName = "processmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "processmasterid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "processname")
	private String processName;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	@Column(name = "screencode", length = 5)
	private String screenCode = "PM";
	@Column(name = "screenname", length = 30)
	private String screenName = "PROCESS MASTER";

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
