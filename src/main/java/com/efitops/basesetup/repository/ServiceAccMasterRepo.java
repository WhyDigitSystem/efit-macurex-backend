package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ServiceAccMasterVO;

public interface ServiceAccMasterRepo extends JpaRepository<ServiceAccMasterVO, Long> {

	boolean existsByServiceNameAndOrgId(String serviceName, Long orgId);

//	@Query(nativeQuery = true, value = "select * from serviceaccmaster where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
//	List<ServiceAccMasterVO> findByOrgIdAndBranch(Long orgId, Long branchId);

	@Query(nativeQuery = true, value = "select * from serviceaccmaster where serviceaccmaster_id=?1 and active=1 and cancel=0")
	ServiceAccMasterVO getServiceAccMasterById(Long id);

	@Query(nativeQuery = true, value = "select * from serviceaccmaster where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ServiceAccMasterVO> getServiceAccMasterByOrgId(Long orgId, Long branchId);

	@Query(value = """
		    SELECT
		        s.serviceaccmaster_id,
		        s.service_name,
		        s.service_description,
		        h.hsn,
		        g.igst,
		        g.cgst,
		        g.sgst,
		        g.rate,
		        h.hsn_id
		    FROM serviceaccmaster s
		    LEFT JOIN hsn h
		        ON h.hsn_id = s.hsn_code
		    LEFT JOIN gstratemaster g
		        ON g.hsn_sac_code = h.hsn_id
		    WHERE s.active = 1
		      AND s.cancel = 0
		      AND h.active = 1
		      AND h.cancel = 0
		      AND g.active = 1
		      AND g.cancel = 0
		      AND s.org_id = :orgId
		      AND s.branch = :branch
		    ORDER BY s.service_name
		    """, nativeQuery = true)
		Set<Object[]> getServiceForSupplierRateContract(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);
}
