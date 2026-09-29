package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionDetailsDTO {

	private Long id;

	private Long item;

	private Long unit;

	private BigDecimal availableStock;

	private BigDecimal recQty;

	private BigDecimal costRate;

	private BigDecimal amount;

	private String note;

}
