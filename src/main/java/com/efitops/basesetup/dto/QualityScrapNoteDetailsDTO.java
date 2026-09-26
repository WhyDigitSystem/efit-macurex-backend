package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityScrapNoteDetailsDTO {

	private Long item;

	private BigDecimal stock;

	private BigDecimal quantity;

	private BigDecimal rate;

	private BigDecimal value;

}
