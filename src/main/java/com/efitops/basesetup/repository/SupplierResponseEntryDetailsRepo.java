package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SupplierResponseEntryDetailsVO;
import com.efitops.basesetup.entity.SupplierResponseEntryVO;

public interface SupplierResponseEntryDetailsRepo extends JpaRepository<SupplierResponseEntryDetailsVO, Long>{

	List<SupplierResponseEntryDetailsVO> findBySupplierResponseEntryVO(SupplierResponseEntryVO supplierResponseEntryVO);

}
