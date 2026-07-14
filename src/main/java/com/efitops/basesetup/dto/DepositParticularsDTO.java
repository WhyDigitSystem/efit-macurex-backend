package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efitops.basesetup.entity.BankingDepositVO;
import com.efitops.basesetup.entity.DepositParticularsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositParticularsDTO {

	private String accountsName;
	private BigDecimal debit;
	private BigDecimal credit;
	private String narration;
}
