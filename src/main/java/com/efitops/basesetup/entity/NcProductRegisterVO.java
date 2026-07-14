package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ncproductregister")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class NcProductRegisterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ncproductregistergen")
	@SequenceGenerator(name = "ncproductregistergen", sequenceName = "ncproductregisterseq", initialValue = 1000000001, allocationSize = 1)

	@Column(name = "ncproductregisterid")
	private Long id;

	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docno")
	private Long docNo;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	@Column(name = "updatedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "screencode", length = 5)
	private String screenCode = "NCPR";

	@Column(name = "screenname", length = 25)
	private String screenName = "NCPRODUCTREGISTER";

	@OneToMany(mappedBy = "ncProductRegisterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<NcProductRegisterDetailsVO> ncProductRegisterDetailsVO;
	
	@OneToMany(mappedBy = "ncProductRegisterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<NCProductRegisterDetailsAttachmentVO> documents;

}
