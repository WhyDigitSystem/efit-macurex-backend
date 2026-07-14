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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquirydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnquiryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirydetailsgen")
	@SequenceGenerator(name = "enquirydetailsgen", sequenceName = "enquirydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquirydetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "partcode")
	private String partCode;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "revisionno")
	private String revisionNo;
	@Column(name = "unit")
	private String unit;
	@Column(name = "requireqty")
	private Long requireQty;
	@Column(name = "deliverydate")
	private LocalDate deliveryDate;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "enquiryid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	EnquiryVO enquiryVO;

}
