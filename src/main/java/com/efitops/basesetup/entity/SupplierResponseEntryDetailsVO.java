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
@Table(name = "supplier_response_entry_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseEntryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_response_entry_detailsgen")
	@SequenceGenerator(name = "supplier_response_entry_detailsgen", sequenceName = "supplier_response_entry_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "supplier_response_entry_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "qty")
	private BigDecimal qty;
	
	@Column(name = "response_qty")
	private BigDecimal responseQty;
	
	@Column(name = "reason")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name = "supplier_response_entry_basic")
	@JsonBackReference
	private SupplierResponseEntryVO supplierResponseEntryVO;

}
