package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ScrapMaterialReturnRejectionDetailsVO;
import com.efitops.basesetup.entity.ScrapMaterialReturnRejectionVO;

public interface ScrapMaterialReturnRejectionDetailsRepo extends JpaRepository<ScrapMaterialReturnRejectionDetailsVO, Long>{

	void deleteByScrapMaterialReturnRejectionVO(ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO);

}
