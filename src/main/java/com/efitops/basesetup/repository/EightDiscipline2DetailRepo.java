package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDiscipline2DetailVO;

@Repository
public interface EightDiscipline2DetailRepo extends JpaRepository<EightDiscipline2DetailVO, Long> {
	
	
	 List<EightDiscipline2DetailVO> findByEightDisciplineEntryVOId(Long id);


}