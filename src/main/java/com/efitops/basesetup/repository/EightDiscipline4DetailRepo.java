package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.efitops.basesetup.entity.EightDiscipline4DetailVO;

@Repository
public interface EightDiscipline4DetailRepo extends JpaRepository<EightDiscipline4DetailVO, Long> {
	
	
	
	 List<EightDiscipline4DetailVO> findByEightDisciplineEntryVOId(Long id);


}