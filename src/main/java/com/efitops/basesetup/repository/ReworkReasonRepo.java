package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ReworkReasonVO;
import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface ReworkReasonRepo extends JpaRepository<ReworkReasonVO, Long> {

    List<ReworkReasonVO> findByProductionEntryVO(ProductionEntryVO vo);
}