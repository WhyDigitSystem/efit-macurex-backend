package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractVO;

@Repository
public interface SupplierRateContractItemDetailsRepo extends JpaRepository<SupplierRateContractItemDetailsVO, Long>{

	Iterable<? extends SupplierRateContractItemDetailsVO> findBySupplierRateContractVO(
			SupplierRateContractVO supplierRateContractVO);

}
