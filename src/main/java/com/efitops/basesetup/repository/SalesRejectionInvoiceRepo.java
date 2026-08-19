package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceRepo extends JpaRepository<SalesRejectionInvoiceVO, Long>{

	@Query(value = "SELECT * " +
            "FROM sales_rejection_invoice_basic " +
            "WHERE org_id = :orgId " +
            "AND branch = :branch " +
            "AND active = 1 " +
            "AND cancel = 0 " +
            "ORDER BY sales_rejection_invoice_basic_id DESC",
    nativeQuery = true)
List<SalesRejectionInvoiceVO> getSalesRejectionInvoiceByOrgId(
     @Param("orgId") Long orgId,
     @Param("branch") Long branch);

	@Query(value = """
		    SELECT s.doc_id
		    FROM sales_rejection_invoice_basic s
		    WHERE s.org_id = ?1
		      AND s.screen_code = ?2
		      AND s.active = 1
		      AND s.cancel = 0
		    ORDER BY s.sales_rejection_invoice_basic_id DESC
		    LIMIT 1
		    """, nativeQuery = true)
		String getSalesRejectionInvoiceDocId(Long orgId, String screenCode);
}
