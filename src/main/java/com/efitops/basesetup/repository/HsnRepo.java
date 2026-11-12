package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;

public interface HsnRepo extends JpaRepository<HsnVO, Long> {
	
	@Query(value = """
            SELECT *
            FROM hsn
            WHERE org_id = :orgId and branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY hsn
            """, nativeQuery = true)
    List<HsnVO> findByOrgId(@Param("orgId") Long orgId, Long branch);

    boolean existsByOrgIdAndCategoryAndHsnIgnoreCase(
            Long orgId,
            ListOfValuesDetailsVO category,
            String hsn);

    @Query(value = """
            SELECT *
            FROM hsn
            WHERE hsn_id = :hsnId
            """, nativeQuery = true)
    Optional<HsnVO> findByHsn_Id(@Param("hsnId") Long hsnId);
}