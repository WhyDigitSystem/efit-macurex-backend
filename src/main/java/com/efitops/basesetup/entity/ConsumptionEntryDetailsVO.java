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
@Table(name = "consumption_entry_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionEntryDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consumption_entry_detailsgen")
	@SequenceGenerator(name = "consumption_entry_detailsgen", sequenceName = "consumption_entry_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "consumption_entry_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "consumed_qty", precision = 10, scale = 2)
	private BigDecimal consumedQty;

	@ManyToOne
	@JoinColumn(name = "consumption_entry_id")
	@JsonBackReference
	private ConsumptionEntryVO consumptionEntryVO;
}