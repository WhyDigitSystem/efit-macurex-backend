package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingDetailVO;
import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;

public interface ProcessSheetCompRoutingDetailRepo extends JpaRepository<ProcessSheetCompRoutingDetailVO, Long> {

	List<ProcessSheetCompRoutingDetailVO> findByProcessSheetCompRoutingVO(ProcessSheetCompRoutingVO processSheetVO);

}
