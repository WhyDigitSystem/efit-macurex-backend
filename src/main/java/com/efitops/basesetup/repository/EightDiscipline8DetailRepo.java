package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.efitops.basesetup.entity.EightDiscipline8DetailVO;

@Repository
public interface EightDiscipline8DetailRepo extends JpaRepository<EightDiscipline8DetailVO, Long> {
	
	
	
	 List<EightDiscipline8DetailVO> findByEightDisciplineEntryVOId(Long id);


}