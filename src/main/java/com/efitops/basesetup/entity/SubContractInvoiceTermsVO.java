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
@Table(name = "subcontractinvoiceterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractInvoiceTermsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractinvoicetermsgen")
	@SequenceGenerator(name = "subcontractinvoicetermsgen", sequenceName = "subcontractinvoicetermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractinvoicetermsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "terms")
	private String terms;
	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "subcontractinvoiceid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	SubContractInvoiceVO subContractInvoiceVO;
}
