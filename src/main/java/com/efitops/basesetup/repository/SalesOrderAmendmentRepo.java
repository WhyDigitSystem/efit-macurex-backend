package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesOrderAmendmentVO;

public interface SalesOrderAmendmentRepo extends JpaRepository<SalesOrderAmendmentVO, Long> {

    @Query(value = """
        SELECT *
        FROM sales_order_amendment_basic
        WHERE sales_order_amendment_id = :id
        """, nativeQuery = true)
    SalesOrderAmendmentVO getSalesOrderAmendmentById(
            @Param("id") Long id);

    @Query(value = """
        SELECT *
        FROM sales_order_amendment_basic
        WHERE org_id = :orgId
          AND branch = :branch
          AND cancel = false
          AND active = true
        ORDER BY sales_order_amendment_id DESC
        """, nativeQuery = true)
    List<SalesOrderAmendmentVO> getSalesOrderAmendmentByOrgId(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

    @Query(value = """
        SELECT COALESCE(MAX(revision_no),0)
        FROM sales_order_amendment_basic
        WHERE salesorder_no = :soNumber
          AND cancel = false
        """, nativeQuery = true)
    Integer findMaxRevisionNo(
            @Param("soNumber") String soNumber);

    @Query(value = """
            SELECT COALESCE(MAX(soa.revision_no), 0) + 1
            FROM sales_order_amendment_basic soa
            INNER JOIN sales_order_amendment_detail soad
                ON soa.sales_order_amendment_id =
                   soad.sales_order_amendment_id
            WHERE soa.salesorder_no = ?1
              AND soad.item = ?2
              AND soa.org_id = ?3
              AND soa.branch = ?4
              AND soa.active = 1
              AND soa.cancel = 0
            """, nativeQuery = true)
    Integer getSalesOrderAmdRevisionNo(
            String salesOrderNo,
            Long item,
            Long orgId,
            Long branch);    
    
    
   
}