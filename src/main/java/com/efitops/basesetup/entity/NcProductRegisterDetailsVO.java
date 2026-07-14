package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "ncproductregisterdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class NcProductRegisterDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ncproductregisterdetailsgen")
	@SequenceGenerator(name = "ncproductregisterdetailsgen", sequenceName = "ncproductregisterdetailsseq", initialValue = 1000000001, allocationSize = 1)

	@Column(name = "ncproductregisterdetailsid")
	private Long id;

	@Column(name = "date")
	private LocalDate date = LocalDate.now();
	@Column(name = "stage")
	private String stage;
	@Column(name = "primarynumber")
	private Integer primaryNumber;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "processdescription")
	private String processDescription;
	@Column(name = "detailsofnonconformance")
	private String detailsOfNonConformance;

	@Column(name = "ncquantity", precision = 10, scale = 2)
	private BigDecimal ncQuantity;
	@Column(name = "unit", precision = 10, scale = 2)
	private BigDecimal unit;

	@Column(name = "correctiveaction")
	private String correctiveaction;
	@Column(name = "caparef")
	private String capaRef;
	@Column(name = "signature")
	private String signature;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "ncproductregisterid")
	@JsonBackReference
	private NcProductRegisterVO ncProductRegisterVO;

}
