package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_tax_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItemTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_tax_detailgen")
	@SequenceGenerator(name = "quotation_tax_detailgen", sequenceName = "quotation_tax_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quotation_tax_detail_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "quotation_id")
	QuotationVO quotationVO;

}
