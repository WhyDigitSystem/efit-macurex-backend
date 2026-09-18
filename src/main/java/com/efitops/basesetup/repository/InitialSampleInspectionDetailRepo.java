package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InitialSampleInspectionDetailVO;

@Repository
public interface InitialSampleInspectionDetailRepo
        extends JpaRepository<InitialSampleInspectionDetailVO, Long> {

    List<InitialSampleInspectionDetailVO>
    findByInitialSampleInspectionVOId(Long initialSampleInspectionVOId);
}