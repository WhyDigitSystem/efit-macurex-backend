package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CostDebitChargesVO;
import com.efitops.basesetup.entity.CostDebitNoteVO;
@Repository
public interface CostDebitChargesRepo extends JpaRepository<CostDebitChargesVO, Long>{

	List<CostDebitChargesVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
