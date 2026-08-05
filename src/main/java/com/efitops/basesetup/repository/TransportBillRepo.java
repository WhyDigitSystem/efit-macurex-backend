package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TransportBillVO;

public interface TransportBillRepo extends JpaRepository<TransportBillVO, Long> {

    boolean existsByBillNoAndOrgId(String billNo, Long orgId);

    boolean existsByBillNoAndOrgIdAndIdNot(String billNo, Long orgId, Long id);

    boolean existsByDocNoAndOrgId(String docNo, Long orgId);

    boolean existsByDocNoAndOrgIdAndIdNot(String docNo, Long orgId, Long id);

    List<TransportBillVO> findByOrgIdAndPlant_Id(Long orgId, Long plantId);
}