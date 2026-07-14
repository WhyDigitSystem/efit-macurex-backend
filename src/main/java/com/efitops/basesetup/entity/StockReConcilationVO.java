package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "stockreconcilation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReConcilationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stockreconcilationgen")
	@SequenceGenerator(name = "stockreconcilationgen", sequenceName = "stockreconcilationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockreconcilationid")
	private Long id;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "location")
	private String location;

	@Column(name = "preparedby")
	private String preparedBy;

	@Column(name = "time")
	private LocalTime time = LocalTime.now();

	@Column(name = "narration")
	private String narration;

	@Column(name = "totalamount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

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
	private String screenCode = "SRC";

	@Column(name = "screenname", length = 25)
	private String screenName = "STOCKRECONCILATION";

	@Column(name = "orgid")
	private Long orgId;

	@OneToMany(mappedBy = "stockReConcilationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<StockReConcilationDetailsVO> stockReConcilationDetailsVO;

}
