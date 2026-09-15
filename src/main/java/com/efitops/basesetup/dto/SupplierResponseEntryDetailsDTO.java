package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseEntryDetailsDTO {

	private Long item;

	private BigDecimal qty;

	private BigDecimal responseQty;

	private String reason;

}
