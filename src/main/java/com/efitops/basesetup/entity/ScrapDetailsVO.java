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
@Table(name = "scrap_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_detailsgen")
	@SequenceGenerator(name = "scrap_detailsgen", sequenceName = "scrap_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "scrap_details_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "scrap")
	private ListOfValuesDetailsVO scrap;

	@Column(name = "weight", precision = 10, scale = 3)
	private BigDecimal weight;

	@Column(name = "qty", precision = 10, scale = 3)
	private BigDecimal qty;

	@ManyToOne
	@JoinColumn(name = "production_entry_basic_id")
	@JsonBackReference
	private ProductionEntryVO productionEntryVO;
}