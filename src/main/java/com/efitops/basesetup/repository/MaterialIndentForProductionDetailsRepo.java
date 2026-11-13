package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialIndentForProductionDetailsVO;
import com.efitops.basesetup.entity.MaterialIndentForProductionVO;

@Repository
public interface MaterialIndentForProductionDetailsRepo extends JpaRepository<MaterialIndentForProductionDetailsVO, Long> {

	List<MaterialIndentForProductionDetailsVO> findByMaterialIndentForProductionVO(MaterialIndentForProductionVO vo);

}
