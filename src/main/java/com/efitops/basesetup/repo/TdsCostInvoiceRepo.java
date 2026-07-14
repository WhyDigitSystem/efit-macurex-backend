package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.CostInvoiceVO;
import com.efitops.basesetup.entity.TdsCostInvoiceVO;

public interface TdsCostInvoiceRepo extends JpaRepository<TdsCostInvoiceVO,Long> {

	List<TdsCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
