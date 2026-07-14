package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialTypeDetailsVO;
import com.efitops.basesetup.entity.MaterialTypeVO;

@Repository
public interface MaterialTypeDetailRepo extends JpaRepository<MaterialTypeDetailsVO, Long> {

	List<MaterialTypeDetailsVO> findByMaterialTypeVO(MaterialTypeVO materialTypeVO);



	boolean existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCase(
			Long orgId, String upperCase, String upperCase2, String itemSubGroup);


	boolean existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCaseAndMaterialTypeVO_IdNot(
			Long orgId, String upperCase, String upperCase2, String itemSubGroup, Long id);
}