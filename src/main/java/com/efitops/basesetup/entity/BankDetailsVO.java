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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bankdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankdetailsgen")
	@SequenceGenerator(name = "bankdetailsgen", sequenceName = "bankdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bankdetailsid")
	private Long id;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "ifsc_code")
	private String ifscCode;
	@Column(name = "account_no")
	private Long accountNo;
	@Column(name = "bank_branch")
	private String bankBranch;

	@Column(name = "screen_code", length = 5)
	private String screenCode = "BD";

	@Column(name = "screen_name", length = 30)
	private String screenName = "BANKDETAILS";

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "branch_id")
	BranchVO branchVO;
}
