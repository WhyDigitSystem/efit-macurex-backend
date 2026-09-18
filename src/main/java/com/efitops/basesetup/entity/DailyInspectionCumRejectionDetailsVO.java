package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "daily_inspection_cum_rejection_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyInspectionCumRejectionDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_inspection_cum_rejection_detailsgen")
	@SequenceGenerator(name = "daily_inspection_cum_rejection_detailsgen", sequenceName = "daily_inspection_cum_rejection_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "daily_inspection_cum_rejection_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fg_item")
	private ItemMasterVO fgItem;
	
	@Column(name = "stock")
	private BigDecimal stock;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "inspection_qty")
	private BigDecimal inspectionQty;
	
	@Column(name = "accepted_qty")
	private BigDecimal acceptedQty;
	
	@Column(name = "rework_qty")
	private BigDecimal reworkQty;
	
	@Column(name = "rejection_qty")
	private BigDecimal rejectionQty;
	
	@Column(name = "scrap_qty")
	private BigDecimal scrapQty;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "daily_inspection_cum_rejection_date_basic_id")
	private DailyInspectionCumRejectionDataVO dailyInspectionCumRejectionDataVO;
	

	
}
