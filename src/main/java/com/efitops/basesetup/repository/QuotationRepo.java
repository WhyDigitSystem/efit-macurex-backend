package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationRepo extends JpaRepository<QuotationVO, Long> {

	@Query(nativeQuery = true, value = "select * from quotation where quotation_id=?1 and active=1 and cancel=0")
	QuotationVO getQuotationById(Long id);

	@Query(nativeQuery = true, value = "select * from quotation where doc_id=?1")
	QuotationVO findByDocId(String docId);

	@Query(nativeQuery = true, value = "select * from quotation where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<QuotationVO> getQuotationByOrgId(Long orgId, Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemappingdetails where org_id=?1 and screen_code=?2")
	String getQuotationByDocId(Long orgId, String screenCode);

}
