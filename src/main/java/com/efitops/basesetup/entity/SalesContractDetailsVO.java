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
@Table(name = "sale_contract_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salecontractdetailsgen")
	@SequenceGenerator(name = "salecontractdetailsgen", sequenceName = "salecontractdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sale_contract_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "old_rate")
	private Double oldRate;
	
	@Column(name = "new_rate")
	private Double newRate;
	
	@Column(name = "valid_from")
	private String validFrom;
	
	@Column(name = "valid_to")
	private String validTo;
	
	@Column(name = "new_validdate")
	private String newValidDate;
	
	@ManyToOne
	@JoinColumn(name = "sale_contract_amendment_basic_id")
	@JsonBackReference
	private SalesContractAmendmentVO salesContractAmendmentVO;

}
