package com.efitops.basesetup.entity;

import javax.persistence.Column;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lme")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class LMEVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lmegen")
	@SequenceGenerator(name = "lmegen", sequenceName = "lmeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "lme_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "currency_name")
	private CurrencyVO currencyName;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	
	@Column(name = "lme_rate")
	private Double lmeRate;
	
	@Column(name = "lme_date_from")
	private String lmeDateFrom;
	
	@Column(name = "elme_date_to")
	private String elmeDateTo;
	
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
	
    @Column(name = "financial_year", length = 5)
    private String finYear;
	@Column(name = "screen_code", length = 30)
	private String screenCode = "LME";
	@Column(name = "screen_name", length = 30)
	private String screenName = "LME";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}
