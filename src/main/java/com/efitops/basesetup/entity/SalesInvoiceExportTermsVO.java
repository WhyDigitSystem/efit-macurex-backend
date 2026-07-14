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
@Table(name = "salesinvoiceexportterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceExportTermsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesinvoiceexporttermsgen")
	@SequenceGenerator(name = "salesinvoiceexporttermsgen", sequenceName = "salesinvoiceexporttermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesinvoiceexporttermsid")
	private Long id;
	@Column(name = "terms")
	private String terms;
	@Column(name = "descriptions")
	private String descriptions;

	@ManyToOne
	@JoinColumn(name = "salesinvoiceexportid")
	@JsonBackReference
	private SalesInvoiceExportVO salesInvoiceExportVO;
}
