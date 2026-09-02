package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractTaxDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractVO;

@Repository
public interface SupplierRateContractTaxDetailsRepo extends JpaRepository<SupplierRateContractTaxDetailsVO, Long>{

	Iterable<? extends SupplierRateContractTaxDetailsVO> findBySupplierRateContractVO(
			SupplierRateContractVO supplierRateContractVO);

}
