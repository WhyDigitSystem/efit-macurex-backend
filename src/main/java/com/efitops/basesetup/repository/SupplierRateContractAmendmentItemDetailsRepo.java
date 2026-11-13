package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractAmendmentItemDetailsVO;

@Repository
public interface SupplierRateContractAmendmentItemDetailsRepo extends JpaRepository<SupplierRateContractAmendmentItemDetailsVO, Long>{

}
