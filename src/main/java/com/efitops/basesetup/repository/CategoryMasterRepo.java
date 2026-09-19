package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.CategoryMasterVO;

public interface CategoryMasterRepo extends JpaRepository<CategoryMasterVO, Long>{
	
	
	

	@Query(value = """
	        SELECT *
	        FROM category_master_basic
	        WHERE cancel = 0
	        AND active = 1
	        AND org_id = :orgId
	        ORDER BY category_master_basic_id DESC
	        """, nativeQuery = true)
	List<CategoryMasterVO> getCategoryMasterByOrgId(
	        @Param("orgId") Long orgId);

}
