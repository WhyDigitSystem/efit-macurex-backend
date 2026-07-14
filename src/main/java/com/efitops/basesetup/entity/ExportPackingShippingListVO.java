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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exportpackingshippinglist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportPackingShippingListVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exportpackingshippinglistgen")
	@SequenceGenerator(name = "exportpackingshippinglistgen", sequenceName = "exportpackingshippinglistseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "exportpackingshippinglistid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	
	@Column(name = "precarriage")
	private String precarriage;
	@Column(name = "plcofrecpbyprecarr")
	private String plcOfRecpByPreCarr;
	@Column(name = "vesselno")
	private String vesselNo;
	@Column(name = "portofloading")
	private String portOfLoading;
	@Column(name = "portofunloading")
	private String portOfUnloading;
	@Column(name = "placeofdelivery")
	private String placeOfDelivery;
	@Column(name = "containerno")
	private String containerNo;
	@Column(name = "noofpackage")
	private String noOfPackage;
	
	@ManyToOne
	@JoinColumn(name = "exportpackinglistid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	ExportPackingListVO exportPackingListVO;
}
