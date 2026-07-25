package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.UomConversionVO;

public interface UomConversionRepo extends JpaRepository<UomConversionVO, Long> {

    List<UomConversionVO> findByOrgId(Long orgId);

    boolean existsByOrgIdAndFromUnitAndToUnit(
            Long orgId,
            Long fromUnit,
            Long toUnit);
}