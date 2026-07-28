package com.efitops.basesetup.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tsbank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSBankVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsbankgen")
	@SequenceGenerator(name = "tsbankgen", sequenceName = "tsbankseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tsbank_id")
	private Long id;
	
	@Column(name = "beneficiary_name")
	private String beneficiary;
	
	@Column(name = "bank_name")
	private String bank;
	
	@Column(name = "ac_no")
	private String acno;
	
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "ifsc_code")
	private String ifscCode;
	
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
	
	
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "BM";
	@Column(name = "screenname", length = 30)
	private String screenName = "Bank Master";

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
