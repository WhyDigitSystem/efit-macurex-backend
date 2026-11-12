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
@Table(name = "grn_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grn_tax_detailsgen")
	@SequenceGenerator(name = "grn_tax_detailsgen", sequenceName = "grn_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grn_tax_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;

	@Column(name = "tax_val", precision = 10, scale = 2)
	private BigDecimal taxVal;

	@Column(name = "tax_amount", precision = 10, scale = 2)
	private BigDecimal taxAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "grn_basic_id")
	GrnVO grnVO;

}
