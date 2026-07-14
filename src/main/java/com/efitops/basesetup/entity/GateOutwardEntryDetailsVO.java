package com.efitops.basesetup.entity;

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
@Table(name = "gateoutwardentrydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateOutwardEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gateoutwardentrydetailsgen")
	@SequenceGenerator(name = "gateoutwardentrydetailsgen", sequenceName = "gateoutwardentrydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gateoutwardentrydetailsid")
	private Long id;
	
	@Column(name = "item")
	private String item;

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "uom")
	private String uom;
	
	@Column(name = "qty")
	private int qty;
	
	@ManyToOne
	@JoinColumn(name = "gateoutwardentryid")
	@JsonBackReference
	private GateOutwardEntryVO gateOutwardEntryVO;
	
	

	
}
