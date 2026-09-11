package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyInspectionCumRejectionDataDetailsDTO {

	private Long fgItem;

	private BigDecimal stock;

	private BigDecimal rate;

	private BigDecimal inspectionQty;

	private BigDecimal acceptedQty;

	private BigDecimal reworkQty;

	private BigDecimal rejectionQty;

	private BigDecimal scrapQty;

}
