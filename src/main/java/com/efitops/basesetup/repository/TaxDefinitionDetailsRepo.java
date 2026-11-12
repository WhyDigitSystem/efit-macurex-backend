package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TaxDefinitionDetailsVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;





@Repository
public interface TaxDefinitionDetailsRepo extends JpaRepository<TaxDefinitionDetailsVO, Long>{

	 List<TaxDefinitionDetailsVO> findByTaxDefinitionVO(
	            TaxDefinitionVO taxDefinitionVO);


	
}
