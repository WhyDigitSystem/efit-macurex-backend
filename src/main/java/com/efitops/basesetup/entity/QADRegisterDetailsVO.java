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
@Table(name = "qadregisterdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QADRegisterDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "qadregisterdetailsgen")
	@SequenceGenerator(name = "qadregisterdetailsgen", sequenceName = "qadregisterdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "qadregisterdetailsid")
	private Long id;

	@Column(name = "date")
	private LocalDate Date = LocalDate.now();

	@Column(name = "documentNo")
	private String documentNo;

	@Column(name = "olddocissue")
	private long olddocissue;

	@Column(name = "olddocrev")
	private String olddocrev;

	@Column(name = "newdocissue")
	private long newdocissue;

	@Column(name = "newdocrev")
	private String newdocrev;

	@Column(name = "admendmentdetails")
	private String admendmentdetails;

	@Column(name = "reasonforadmendment")
	private String reasonforadmendment;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "reviewedby")
	private String reviewedby;

	@ManyToOne
	@JoinColumn(name = "qadregisterid")
	@JsonBackReference
	private QADRegisterVO qadRegisterVO;

}
