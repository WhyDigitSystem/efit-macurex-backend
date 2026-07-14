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
@Table(name = "npddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpdDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "npddetailsgen")
	@SequenceGenerator(name = "npddetailsgen", sequenceName = "npddetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "npddetailsid")
	private Long id;

	@Column(name = "documentrefno")
	private String documentRefNo;

	@Column(name = "customer")
	private String customer;

	@Column(name = "partno")
	private String partNo;

	@Column(name = "partname")
	private String partName;

	@Column(name = "currentdate")
	private LocalDate currentDate;

	@Column(name = "revision")
	private String revision;

	@Column(name = "approvedby")
	private String approvedBy;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "npdid")
	@JsonBackReference
	private NpdVO npdVO;

}
