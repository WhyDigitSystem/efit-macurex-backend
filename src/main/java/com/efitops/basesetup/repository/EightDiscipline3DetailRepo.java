package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDiscipline3DetailVO;

@Repository
public interface EightDiscipline3DetailRepo extends JpaRepository<EightDiscipline3DetailVO, Long> {
	
	
	 List<EightDiscipline3DetailVO> findByEightDisciplineEntryVOId(Long id);


}