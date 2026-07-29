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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "globalparam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParameterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalparamgen")
	@SequenceGenerator(name = "globalparamgen", sequenceName = "globalparamseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "globalparam_id")
	private Long id;

	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "user_id")
	private Long userid;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branch_code")
	private String branchcode;
	@Column(name = "financial_year")
	private String finYear;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}

