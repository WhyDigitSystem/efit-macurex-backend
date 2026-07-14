package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessDoneDetailsVO;
import com.efitops.basesetup.entity.ProcessDoneVO;

public interface ProcessDoneDetailsRepo extends JpaRepository<ProcessDoneDetailsVO, Long> {

	List<ProcessDoneDetailsVO> findByProcessDoneVO(ProcessDoneVO processDoneVO);

}
