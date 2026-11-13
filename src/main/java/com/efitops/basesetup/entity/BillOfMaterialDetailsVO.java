package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "bill_of_material_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_of_material_detailsgen")
	@SequenceGenerator(name = "bill_of_material_detailsgen", sequenceName = "bill_of_material_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bill_of_material_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;


	@Column(name = "item_type")
	private String itemType;

	@ManyToOne
	@JoinColumn(name = "uom")
	private UnitMasterVO uom;

	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;

	@Column(name = "qty", precision = 15, scale = 5)
	private BigDecimal qty;

	@Column(name = "manbou")
	private String manbou;

	@Column(name = "sfg_bom_ref_no")
	private String sfgBomRefNo;

	@Column(name = "sfg_bom_ref_date")
	private LocalDate sfgBomRefDate;

	@Column(name = "scrap_item")
	private String scrapItem;

	@ManyToOne
	@JoinColumn(name = "scrap_unit")
	private UnitMasterVO scrapUnit;

	@Column(name = "scrap_qty", precision = 10, scale = 2)
	private BigDecimal scrapQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "bill_of_material_id")
	private BillOfMaterialVO billOfMaterialVO;

}
