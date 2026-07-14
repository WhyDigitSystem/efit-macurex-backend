package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CostDebitNoteTaxPrtculVO;
import com.efitops.basesetup.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteTaxPrtculRepo extends JpaRepository<CostDebitNoteTaxPrtculVO, Long>{

	List<CostDebitNoteTaxPrtculVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
