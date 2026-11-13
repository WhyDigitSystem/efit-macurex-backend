package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingMachineVO;
import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;

public interface ProcessSheetCompRoutingMachineRepo extends JpaRepository<ProcessSheetCompRoutingMachineVO, Long> {

	List<ProcessSheetCompRoutingMachineVO> findByProcessSheetCompRoutingVO(ProcessSheetCompRoutingVO processSheetVO);

}
