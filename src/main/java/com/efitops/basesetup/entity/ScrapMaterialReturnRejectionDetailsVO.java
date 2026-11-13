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
@Table(name = "scrap_material_return_rejection_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_material_return_rejection_detailsgen")
	@SequenceGenerator(name = "scrap_material_return_rejection_detailsgen", sequenceName = "scrap_material_return_rejection_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "scrap_material_return_rejection_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;
	
	@Column(name = "available_stock")
	private BigDecimal availableStock;
	
	@Column(name = "rec_qty")
	private BigDecimal recQty;
	
	@Column(name = "cost_rate")
	private BigDecimal costRate;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "note")
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "scrap_material_return_rejection_basic_id")
	@JsonBackReference
	private ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO;
	

}
