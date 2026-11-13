package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TaxDefinitionVO;

@Repository
public interface TaxDefinitionRepo extends JpaRepository<TaxDefinitionVO, Long> {

	boolean existsByTaxNoAndOrgId(Long taxNo, Long orgId);

	@Query(value = "SELECT * FROM taxbasic WHERE org_id = ?1 AND branch = ?2", nativeQuery = true)
	List<TaxDefinitionVO> getTaxDefinitionByOrgId(Long orgId, Long branch);

	
}
