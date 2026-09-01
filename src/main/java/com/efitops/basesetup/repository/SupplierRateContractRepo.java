package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractVO;

@Repository
public interface SupplierRateContractRepo extends JpaRepository<SupplierRateContractVO, Long> {

	@Query(value = """
			SELECT *
			FROM supplier_rate_contract
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0 and active=1
			ORDER BY supplier_rate_contract_id DESC
			""", nativeQuery = true)
	List<SupplierRateContractVO> findByOrgIdAndBranch(Long orgId, Long branch);

	@Query(value = """
			SELECT CONCAT(
			    :screenCode,
			    LPAD(
			        COALESCE(
			            MAX(
			                CAST(
			                    SUBSTRING(doc_id, 4)
			                    AS UNSIGNED
			                )
			            ), 0
			        ) + 1,
			        6,
			        '0'
			    )
			)
			FROM supplier_rate_contract
			WHERE org_id = :orgId
			  AND financial_year = :financialYear
			  AND screen_code = :screenCode
			""", nativeQuery = true)
	String getSupplierRateContractDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);
}
