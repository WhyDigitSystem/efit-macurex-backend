package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionBulkIssuesDetailsVO;
import com.efitops.basesetup.entity.ProductionBulkIssuesVO;

@Repository
public interface ProductionBulkIssuesDetailsRepo extends JpaRepository<ProductionBulkIssuesDetailsVO, Long> {

	List<ProductionBulkIssuesDetailsVO> findByProductionBulkIssuesVO(ProductionBulkIssuesVO productionBulkIssuesVO);

}
