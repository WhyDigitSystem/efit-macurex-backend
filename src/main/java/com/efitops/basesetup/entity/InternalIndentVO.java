package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "internal_indent_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internal_indent_basicgen")
	@SequenceGenerator(name = "internal_indent_basicgen", sequenceName = "internal_indent_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "internal_indent_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "belongs_to")
	private String belongTo;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@Column(name = "time_of_indent")
	private String timeOfIndent;
	
	@Column(name = "approved_by_pm")
	private String approvedByPM;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;
	
	@ManyToOne
	@JoinColumn(name = "authorized_by")
	private EmployeeMasterVO authorizedBy;
	
	@Column(name = "remarks")
	private String remarks;
	
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;
    
    @Column(name = "screen_code")
    private String screenCode = "INTI";

    @Column(name = "screen_name")
    private String screenName = "INTERNAL INDENT";
	
    @OneToMany(mappedBy = "internalIndentVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InternalIndentDetailsVO> internalIndentDetailsVO = new ArrayList<>();
	
    @JsonGetter("active")
   	public String getActive() {
   		return active ? "Active" : "In-Active";
   	}

   	@JsonGetter("cancel")
   	public String getCancel() {
   		return cancel ? "T" : "F";
   	}
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

}
