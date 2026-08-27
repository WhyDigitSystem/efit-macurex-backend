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
@Table(name = "internal_indent_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internal_indent_detailsgen")
	@SequenceGenerator(name = "internal_indent_detailsgen", sequenceName = "internal_indent_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "internal_indent_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@Column(name = "required_qty")
	private BigDecimal requiredQty;

	@Column(name = "purpose")
	private String purpose;

	@ManyToOne
	@JoinColumn(name = "internal_indent_basic_id")
	@JsonBackReference
	private InternalIndentVO internalIndentVO;

}
