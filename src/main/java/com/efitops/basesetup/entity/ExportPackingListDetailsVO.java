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
@Table(name = "exportpackinglistdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportPackingListDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exportpackinglistdetailsgen")
	@SequenceGenerator(name = "exportpackinglistdetailsgen", sequenceName = "exportpackinglistdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "exportpackinglistdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "custpo")
	private String custpo;
	@Column(name = "customerpoitem")
	private String customerPoItem;
	@Column(name = "hsncode")
	private String hsnCode;
	@Column(name = "pono")
	private String poNo;
	@Column(name = "quantity")
	private BigDecimal quantity;
	@Column(name = "poquantity")
	private BigDecimal poQuantity;
	@Column(name = "unit")
	private String unit;
	@Column(name = "weightkg")
	private BigDecimal weightKg;
	@Column(name = "price")
	private String price;
	@Column(name = "sano")
	private String sano;
	@Column(name = "wono1")
	private String wono1;
	
	@ManyToOne
	@JoinColumn(name = "exportpackinglistid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	ExportPackingListVO exportPackingListVO;
}
