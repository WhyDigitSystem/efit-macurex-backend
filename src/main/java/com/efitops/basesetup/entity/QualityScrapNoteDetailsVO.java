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
@Table(name = "quality_scrap_note_details")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QualityScrapNoteDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_scrap_note_detailsgen")
	@SequenceGenerator(name = "quality_scrap_note_detailsgen", sequenceName = "quality_scrap_note_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quality_scrap_note_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "stock")
	private BigDecimal stock;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "value")
	private BigDecimal value;
	
	@ManyToOne
	@JoinColumn(name = "quality_scrap_note_basic_id")
	@JsonBackReference
	private QualityScrapNoteVO qualityScrapNoteVO;

}
