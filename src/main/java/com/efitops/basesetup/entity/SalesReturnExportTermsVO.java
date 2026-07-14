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
@Table(name = "salesreturnexportterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnExportTermsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesreturnexporttermsgen")
	@SequenceGenerator(name = "salesreturnexporttermsgen", sequenceName = "salesreturnexporttermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesreturnexporttermsid")
	private Long id;
	@Column(name = "terms")
	private String terms;
	@Column(name = "descriptions")
	private String descriptions;
	@ManyToOne
	@JoinColumn(name = "salesreturnexportid")
	@JsonBackReference
	private SalesReturnExportVO salesReturnExportVO;;
}
