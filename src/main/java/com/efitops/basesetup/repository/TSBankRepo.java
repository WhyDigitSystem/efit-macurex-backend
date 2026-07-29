package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface TSBankRepo extends JpaRepository<TSBankVO, Long>{

	boolean existsByBankAndOrgId(String bank, Long orgId);

	List<TSBankVO> getBankMasterByOrgId(Long orgId);
	@Query(value = """
	        SELECT *
	        FROM bankdetails
	        WHERE org_id = :orgId
	          AND cancel = false and active = 1
	        ORDER BY beneficiary_name
	        """, nativeQuery = true)
	List<TSBankVO> getBankMasterByOrgId1(@Param("orgId") Long orgId
	                                             );

}
