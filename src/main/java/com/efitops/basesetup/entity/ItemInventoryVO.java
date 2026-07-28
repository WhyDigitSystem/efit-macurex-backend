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
@Table(name = "iteminventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventoryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iteminventorygen")
	@SequenceGenerator(name = "iteminventorygen", sequenceName = "iteminventoryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "iteminventory_id",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "manufacured")
	private String manufacured;

	@Column(name = "default_location")
	private String defaultLocation;

	@Column(name = "alternate_location")
	private String alternateLocation;

	@Column(name = "lead_time", precision = 10, scale = 2)
	private BigDecimal leadTime;

	@Column(name = "reorder_level")
	private String reorderLevel;

	@Column(name = "rack_no")
	private String rackNo;

	@Column(name = "row_no")
	private String rowNo;

	@Column(name = "position")
	private String position;

	@Column(name = "minimum_order_qty", precision = 10, scale = 2)	
	private BigDecimal minimumOrderQty;

	@Column(name = "maximum_order_qty", precision = 10, scale = 2)
	private BigDecimal maximumOrderQty;

	@Column(name = "bin_size", precision = 10, scale = 2)
	private String binSize;

	@Column(name = "bin_qty", precision = 10, scale = 2)
	private BigDecimal binQty;
	
//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "itemmaster_id")
//	ItemMasterVO itemMasterVO;
}
