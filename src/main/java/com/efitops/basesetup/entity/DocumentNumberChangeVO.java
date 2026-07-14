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
@Table(name = "documentnumberchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNumberChangeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "documentnumberchangegen")
	@SequenceGenerator(name = "documentnumberchangegen", sequenceName = "documentnumberchangeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documentnumberchangeid")
	private Long id;

	@Column(name = "docid")
	private String docId;

//	@Column(name = "docdate")
//	private LocalDate docDate = LocalDate.now();
	
	@Column(name = "docdate")
	private LocalDate docDate;

	@Column(name = "documentscreenname")
	private String documentScreenName;


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
	private String screenCode = "	";

	@Column(name = "screenname", length = 25)
	private String screenName = "DOCUMENTNUMBERCHANGE";

	@Column(name = "orgid")
	private Long orgId;

	@OneToMany(mappedBy = "documentNumberChangeVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DocumentNumberChangeDetailsVO> documentNumberChangeDetailsVO;

}
