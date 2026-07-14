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
@Table(name = "packinglistdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packinglistdetailsgen")
	@SequenceGenerator(name = "packinglistdetailsgen", sequenceName = "packinglistdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "packinglistdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "salesorderno")
	private String salesOrderNo;
	@Column(name = "poqty")
	private BigDecimal poQty;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "packinglistid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private PackingListVO packingListVO;
}