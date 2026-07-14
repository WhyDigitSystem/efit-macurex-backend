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
@Table(name = "bomdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bomdetailsgen")
	@SequenceGenerator(name = "bomdetailsgen", sequenceName = "bomdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bomdetails")
	private Long id;
	@Column(name="itemtype")
	private String itemType;
	@Column(name="itemcode")
	private String itemCode;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="uom")
	private String uom;
	@Column(name="qty")
	private BigDecimal qty;
	
	
	@ManyToOne
	@JoinColumn(name = "bomid")
	@JsonBackReference
	private BomVO bomVO;

}
