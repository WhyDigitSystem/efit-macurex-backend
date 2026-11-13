package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionEntryDetailsVO;
import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface ProductionEntryDetailsRepo extends JpaRepository<ProductionEntryDetailsVO, Long> {

    List<ProductionEntryDetailsVO> findByProductionEntryVO(ProductionEntryVO vo);
}