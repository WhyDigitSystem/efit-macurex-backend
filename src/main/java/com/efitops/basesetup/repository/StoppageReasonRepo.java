package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StoppageReasonVO;
import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface StoppageReasonRepo extends JpaRepository<StoppageReasonVO, Long> {

    List<StoppageReasonVO> findByProductionEntryVO(ProductionEntryVO vo);
}
