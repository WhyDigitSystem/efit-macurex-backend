package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FgStockUpdateDetailsVO;
import com.efitops.basesetup.entity.FinalFgPartStockUpdateVO;

@Repository
public interface FgStockUpdateDetailsRepo extends JpaRepository<FgStockUpdateDetailsVO, Long>{

	List<FgStockUpdateDetailsVO> findByFinalFgPartStockUpdateVO(FinalFgPartStockUpdateVO finalFgPartStockUpdateVO);

}
