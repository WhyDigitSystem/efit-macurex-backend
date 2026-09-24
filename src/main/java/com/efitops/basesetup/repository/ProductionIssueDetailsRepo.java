package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionIssueDetailsVO;
import com.efitops.basesetup.entity.ProductionIssueVO;

@Repository
public interface ProductionIssueDetailsRepo extends JpaRepository<ProductionIssueDetailsVO, Long> {

	List<ProductionIssueDetailsVO> findByProductionIssueVO(ProductionIssueVO vo);

}
