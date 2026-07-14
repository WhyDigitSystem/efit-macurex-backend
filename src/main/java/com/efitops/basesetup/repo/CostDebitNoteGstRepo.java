package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CostDebitNoteGstVO;
import com.efitops.basesetup.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteGstRepo extends JpaRepository<CostDebitNoteGstVO, Long>{

	List<CostDebitNoteGstVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
