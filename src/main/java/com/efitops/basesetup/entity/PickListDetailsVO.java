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
@Table(name = "picklistdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picklistdetailsgen")
	@SequenceGenerator(name = "picklistdetailsgen", sequenceName = "picklistdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "picklistdetailsid")
	private Long id;

	@Column(name = "item")
	private String item;

	@Column(name = "itemname")
	private String itemName;

	@Column(name = "unit")
	private String unit;

	@Column(name = "rackno")
	private String rackNo;

	@Column(name = "rackqty", precision = 10, scale = 2)
	private BigDecimal rackQty;

	@Column(name = "issuedqty", precision = 10, scale = 2)
	private BigDecimal issuedQty;

	@Column(name = "pickedqty", precision = 10, scale = 2)
	private BigDecimal pickedQty;

	@Column(name = "remainingqty", precision = 10, scale = 2)
	private BigDecimal remainingQty;

	@Column(name = "actualqty", precision = 10, scale = 2)
	private BigDecimal actualQty;

	@Column(name = "flag")
	private boolean flag;

	@ManyToOne
	@JoinColumn(name = "picklistid")
	@JsonBackReference
	private PickListVO pickListVO;

}
