package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseVO;

@Repository
public interface SalesOrderShortCloseRepo extends JpaRepository<SalesOrderShortCloseVO, Long> {

	@Query(nativeQuery = true, value = "select * from salesordershortclose where salesordershortclose_id=?1 and active=1 and cancel=0")
	SalesOrderShortCloseVO getSalesOrderShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select * from salesordershortclose where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<SalesOrderShortCloseVO> getSalesOrderShortCloseByOrgId(Long orgId, Long branchId);

}
