package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DespatchInstructionDetailsVO;
import com.efitops.basesetup.entity.DespatchInstructionVO;

public interface DespatchInstructionDetailsRepo extends JpaRepository<DespatchInstructionDetailsVO, Long>{

	List<DespatchInstructionDetailsVO> findByDespatchInstructionVO(DespatchInstructionVO despatchInstructionVO);

}
