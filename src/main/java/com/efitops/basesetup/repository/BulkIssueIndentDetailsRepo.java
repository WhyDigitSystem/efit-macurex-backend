package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BulkIssueIndentDetailsVO;
import com.efitops.basesetup.entity.BulkIssueIndentVO;

@Repository
public interface BulkIssueIndentDetailsRepo extends JpaRepository<BulkIssueIndentDetailsVO, Long>{

	List<BulkIssueIndentDetailsVO> findByBulkIssueIndentVO(BulkIssueIndentVO bulkIssueIndentVO);

}
