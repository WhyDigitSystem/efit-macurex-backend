package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractItemDetailsVO;

@Repository
public interface SupplierRateContractItemDetailsRepo extends JpaRepository<SupplierRateContractItemDetailsVO, Long>{

}
