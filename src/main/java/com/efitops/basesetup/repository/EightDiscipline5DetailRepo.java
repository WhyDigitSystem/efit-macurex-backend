package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDiscipline5DetailVO;

@Repository
public interface EightDiscipline5DetailRepo extends JpaRepository<EightDiscipline5DetailVO, Long> {

	
	
	 List<EightDiscipline5DetailVO> findByEightDisciplineEntryVOId(Long id);

}