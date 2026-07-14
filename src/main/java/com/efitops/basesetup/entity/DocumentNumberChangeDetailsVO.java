package com.efitops.basesetup.entity;

import java.time.LocalDate;

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
@Table(name = "documentnumberchangedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNumberChangeDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentnumberchangedetailsgen")
	@SequenceGenerator(name = "documentnumberchangedetailsgen", sequenceName = "documentnumberchangedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documentnumberchangedetailsid")
	private Long id;

	@Column(name = "documentformateno")
	private String documentFormateNo;

	@Column(name = "issueno")
	private Long issueNo;

	@Column(name = "revisionno")
	private Long revisionNo;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "documentnumberchangeid")
	@JsonBackReference
	private DocumentNumberChangeVO documentNumberChangeVO;

}
