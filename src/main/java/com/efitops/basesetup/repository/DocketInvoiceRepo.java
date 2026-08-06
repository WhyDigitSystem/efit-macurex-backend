package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DocketInvoiceVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface DocketInvoiceRepo extends JpaRepository<DocketInvoiceVO, Long>{
	
	@Query(value = """
	        SELECT *
	        FROM docket_invoice_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY docket_invoice_basic_id
	        """, nativeQuery = true)
	List<DocketInvoiceVO> getDocketInvoiceByOrgId(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);

}
