package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface TransportRepo extends JpaRepository<TransportMasterVO, Long>{

	boolean existsByTransportNameAndOrgId(String transportName, Long orgid);


	@Query(value = """
	        SELECT *
	        FROM transport
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY transport_name
	        """, nativeQuery = true)
	List<TransportMasterVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);


}
