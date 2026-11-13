package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDiscipline1DetailVO;

@Repository
public interface EightDiscipline1DetailRepo extends JpaRepository<EightDiscipline1DetailVO, Long> {
	
	
	 List<EightDiscipline1DetailVO> findByEightDisciplineEntryVOId(Long id);


}