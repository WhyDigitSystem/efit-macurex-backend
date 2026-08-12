package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_contract_amendment_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAmdDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_contract_amendment_detailgen")
	@SequenceGenerator(name = "sales_contract_amendment_detailgen", sequenceName = "sales_contract_amendment_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sales_contract_amendment_detail_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "old_rate")
	private double oldRate;

	@Column(name = "new_rate")
	private double newRate;

	@Column(name = "valid_from")
	private LocalDate validFrom;

	@Column(name = "valid_to")
	private LocalDate validTo;

	@Column(name = "new_validdate")
	private LocalDate newValidDate;

	@ManyToOne
	@JoinColumn(name = "sales_contract_amendment_basic_id")
	@JsonBackReference
	private SalesContractAmendmentVO salesContractAmendmentVO;
}