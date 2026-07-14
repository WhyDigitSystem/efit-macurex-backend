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
@Table(name = "qadregister")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QADRegisterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "qadregistergen")
	@SequenceGenerator(name = "qadregistergen", sequenceName = "qadregisterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "qadregisterid")
	private Long id;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "docname")
	private String docname;

	@Column(name = "docformatno")
	private String docformatno;

	@Column(name = "approvedby")
	private String approvedby;

	@Column(name = "narration")
	private String narration;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifyby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "screencode", length = 5)
	private String screenCode = "QAD";

	@Column(name = "screenname", length = 25)
	private String screenName = "QADREGISTER";

	@Column(name = "orgid")
	private Long orgId;

	private String summary;

	@OneToMany(mappedBy = "qadRegisterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QADRegisterDetailsVO> qadRegisterDetailsVO;

}
