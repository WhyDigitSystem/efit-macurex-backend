package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.GradeMasterVO;

public interface GradeMasterRepo extends JpaRepository<GradeMasterVO, Long> {

	boolean existsByOrgIdAndGradeCodeIgnoreCase(Long orgId, String gradeCode);

	List<GradeMasterVO> findByOrgId(Long orgId);

}
