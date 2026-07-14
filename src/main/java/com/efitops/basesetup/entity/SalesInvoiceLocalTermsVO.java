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
@Table(name = "salesinvoicelocalterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceLocalTermsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesinvoicelocaltermsgen")
	@SequenceGenerator(name = "salesinvoicelocaltermsgen", sequenceName = "salesinvoicelocaltermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesinvoicelocaltermsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "terms")
	private String terms;
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "salesinvoicelocalid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	SalesInvoiceLocalVO salesInvoiceLocalVO;

}
