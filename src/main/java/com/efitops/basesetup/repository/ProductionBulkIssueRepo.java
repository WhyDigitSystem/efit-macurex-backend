package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionBulkIssueVO;

@Repository
public interface ProductionBulkIssueRepo extends JpaRepository<ProductionBulkIssueVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getProductionBulkIssueDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select * from production_bulk_issue_basic where production_bulk_issue_basic_id=?1 and active=1 and cancel=0")
    ProductionBulkIssueVO getProductionBulkIssueById(Long id);

    @Query(nativeQuery = true, value = "select * from production_bulk_issue_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<ProductionBulkIssueVO> getProductionBulkIssueByOrgId(Long orgId, Long branch);
}
