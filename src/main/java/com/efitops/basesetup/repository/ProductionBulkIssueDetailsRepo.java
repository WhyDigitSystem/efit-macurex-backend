package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionBulkIssueDetailsVO;
import com.efitops.basesetup.entity.ProductionBulkIssueVO;

@Repository
public interface ProductionBulkIssueDetailsRepo extends JpaRepository<ProductionBulkIssueDetailsVO, Long> {

    List<ProductionBulkIssueDetailsVO> findByProductionBulkIssueVO(ProductionBulkIssueVO productionBulkIssueVO);
}