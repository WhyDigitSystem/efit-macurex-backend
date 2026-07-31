package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gstratemaster")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GSTRateMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gstratemastergen")
	@SequenceGenerator(name = "gstratemastergen", sequenceName = "gstratemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gstratemaster_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private ListOfValuesDetailsVO category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_sac_code")
    private HsnVO hsnSacCode;

    @Column(name = "description")
    private String description;

    @Column(name = "wef", precision = 10, scale = 2)
    private LocalDate  wef;

    @Column(name = "taxable")
    private boolean taxable;

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "igst", precision = 10, scale = 2)
    private BigDecimal igst;

    @Column(name = "sgst", precision = 10, scale = 2)
    private BigDecimal sgst;

    @Column(name = "cgst", precision = 10, scale = 2)
    private BigDecimal cgst;

    @Column(name = "duplicate_check")
    private boolean duplicateCheck;
	
	
	
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
	private boolean cancel=false;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
    @Column(name = "financial_year", length = 5)
    private String financialYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "GSTRM";
	@Column(name = "screenname", length = 30)
	private String screenName = "GSTRateMaster";

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



