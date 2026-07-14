package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentNumberChangeVO;

@Repository
public interface DocumentNumberChangeRepo extends JpaRepository<DocumentNumberChangeVO, Long> {

	@Query(nativeQuery = true, value = "select * from documentnumberchange  where  orgid=?1 and finyear=?2 and branchcode=?3")
	List<DocumentNumberChangeVO> getDocumentNumberChangeByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from documentnumberchange  where  documentnumberchangeid=?1")
	DocumentNumberChangeVO getDocumentNumberChangeById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getDocumentNumberChangeDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
