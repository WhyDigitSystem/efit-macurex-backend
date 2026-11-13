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
public class MachineToolsScrapNoteDetailsDTO {
	
	private Long item;

	private BigDecimal stock;

	private BigDecimal quantity;

	private BigDecimal rate;

	private BigDecimal value;

}
