package com.efitops.basesetup.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "designation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DesignationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designationgen")
	@SequenceGenerator(name = "designationgen", sequenceName = "designationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "designation_id")
	private Long id;
	
//	@Column(name = "doc_id")
//	private String docId;
	
    @Column(name = "designation", length = 30)
    private String designation;
    
    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name = "active")
	private boolean active;
	
//	@ManyToOne
//	@JoinColumn(name = "branch")
//	private BranchVO branch;
	
    @Column(name = "financial_year", length = 5)
    private String finYear;
	@Column(name = "screen_code",length = 10)
	private String screenCode ="DSG";
	@Column(name = "screen_name",length = 30)
	private String screenName="DESIGNATION";
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
}
