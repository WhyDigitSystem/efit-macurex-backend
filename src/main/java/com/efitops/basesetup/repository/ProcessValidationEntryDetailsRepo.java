package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProcessValidationEntryDetailsVO;
import com.efitops.basesetup.entity.ProcessValidationEntryVO;

@Repository
public interface ProcessValidationEntryDetailsRepo extends JpaRepository<ProcessValidationEntryDetailsVO, Long>{

	List<ProcessValidationEntryDetailsVO> findByProcessValidationEntryVO(ProcessValidationEntryVO vo);

}
