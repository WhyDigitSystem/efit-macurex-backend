package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.EnquiryVO;

public interface EnquiryRepo extends JpaRepository<EnquiryVO, Long> {

    @Query(value = """
            SELECT *
            FROM enquiry
            WHERE org_id = :orgId
              AND branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY enquiry_id
            """, nativeQuery = true)
    List<EnquiryVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

    boolean existsByEnquiryNoAndOrgId(
            String enquiryNo,
            Long orgId);

}