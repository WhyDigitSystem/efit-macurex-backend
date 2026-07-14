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
@Table(name = "putawaydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutawayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "putawaydetailsgen")
	@SequenceGenerator(name = "putawaydetailsgen", sequenceName = "putawaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawaydetailsid")
	private Long id;

	@Column(name = "item")
	private String item;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "unit")
	private String unit;
	@Column(name = "recqty", precision = 10, scale = 2)
	private BigDecimal recQty;
	@Column(name = "putawayqty", precision = 10, scale = 2)
	private BigDecimal putawayQty;
	@Column(name = "rackno")
	private String rackNo;

	@ManyToOne
	@JoinColumn(name = "putawayid")
	@JsonBackReference
	private PutawayVO putawayVO;
}
