package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ScrapDetailsVO;
import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface ScrapDetailsRepo extends JpaRepository<ScrapDetailsVO, Long> {

    List<ScrapDetailsVO> findByProductionEntryVO(ProductionEntryVO vo);
}
