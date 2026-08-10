package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efitops.basesetup.ResponseDTO.HsnResponseDTO;
import com.efitops.basesetup.entity.HsnVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HsnResponseDetailsTaxDTO {
	private Long id;

	private Long hsnCodeDetails;
	
	private BigDecimal taxPercentage;

	private BigDecimal igstRate;

	private BigDecimal sgstRate;

	private BigDecimal cgstRate;

}
