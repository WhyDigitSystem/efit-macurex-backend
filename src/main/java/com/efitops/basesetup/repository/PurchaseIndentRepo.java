package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.PurchaseIndentVO;

public interface PurchaseIndentRepo
        extends JpaRepository<PurchaseIndentVO, Long> {

	@Query(value = """
	        SELECT *
	        FROM indent_basic p
	        WHERE p.org_id = :orgId
	          AND p.branch = :branch
	          AND p.cancel = false
	          AND p.active = true
	        ORDER BY p.indent_basic_id DESC
	        """, nativeQuery = true)
	List<PurchaseIndentVO> findByPurchaseIndentByOrgId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

    @Query(value = """
            SELECT COALESCE(MAX(doc_id),0)
            FROM Indent_Basic
            """, nativeQuery = true)
    String findLastIndentNo();

}