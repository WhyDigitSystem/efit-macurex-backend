package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuoteRevisionVO;

@Repository
public interface QuoteRevisionRepo extends JpaRepository<QuoteRevisionVO, Long> {

	@Query(nativeQuery = true, value = "select * from quoterevision where orgid=?1 and  docid=?2")
	List<QuoteRevisionVO> getCountQuoteRevision(Long orgId, String docId);

}
