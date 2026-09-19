package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InitialStageInspectionDetailVO;

@Repository
public interface InitialStageInspectionDetailRepo
        extends JpaRepository<InitialStageInspectionDetailVO, Long> {

    List<InitialStageInspectionDetailVO>
    findByInitialStageInspectionVOId(Long initialStageInspectionVOId);

}