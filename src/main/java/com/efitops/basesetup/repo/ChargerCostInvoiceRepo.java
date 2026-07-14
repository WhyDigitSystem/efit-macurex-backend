package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ChargerCostInvoiceVO;
import com.efitops.basesetup.entity.CostInvoiceVO;

public interface ChargerCostInvoiceRepo extends JpaRepository<ChargerCostInvoiceVO, Long> {

	List<ChargerCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
