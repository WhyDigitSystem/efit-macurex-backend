package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.efitops.basesetup.entity.EightDiscipline7DetailVO;

@Repository
public interface EightDiscipline7DetailRepo extends JpaRepository<EightDiscipline7DetailVO, Long> {
	
	
	
	 List<EightDiscipline7DetailVO> findByEightDisciplineEntryVOId(Long id);


}