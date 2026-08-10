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
@Table(name = "order_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceTaxDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_tax_detailsgen")
	@SequenceGenerator(name = "order_tax_detailsgen", sequenceName = "order_tax_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "order_tax_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "accepted_qty_amount", precision = 10, scale = 2)
	private BigDecimal acceptedQtyAmount;

	@Column(name = "revised_amount", precision = 10, scale = 2)
	private BigDecimal revisedAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "order_acceptance_basic_id")
	OrderAcceptanceVO orderAcceptanceVO;

}
