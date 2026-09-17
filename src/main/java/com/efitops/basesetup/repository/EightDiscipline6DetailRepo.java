package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDiscipline6DetailVO;

@Repository
public interface EightDiscipline6DetailRepo extends JpaRepository<EightDiscipline6DetailVO, Long> {
	
	
	
	 List<EightDiscipline6DetailVO> findByEightDisciplineEntryVOId(Long id);


}