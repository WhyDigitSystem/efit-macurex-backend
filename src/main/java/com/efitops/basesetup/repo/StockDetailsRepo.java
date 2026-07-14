package com.efitops.basesetup.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockDetailsVO;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetailsVO, Long> {

	List<StockDetailsVO> findAllByOrgIdAndSourceIdAndDocIdAndCustomer(Long orgId, Long sourceId, String docId,
			String customer);

	@Transactional
	@Modifying
	@Query("DELETE FROM StockDetailsVO s " + "WHERE s.orgId = :orgId " + "AND s.sourceId = :sourceId "
			+ "AND s.docId = :docId " + "AND s.customer = :customer")
	void deleteByOrgIdAndSourceIdAndDocIdAndCustomer(@Param("orgId") Long orgId, @Param("sourceId") Long sourceId,
			@Param("docId") String docId, @Param("customer") String customer);

}
