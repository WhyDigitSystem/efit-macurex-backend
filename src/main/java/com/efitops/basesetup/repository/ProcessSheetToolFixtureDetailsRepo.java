package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;
import com.efitops.basesetup.entity.ProcessSheetToolFixtureDetailsVO;

public interface ProcessSheetToolFixtureDetailsRepo extends JpaRepository<ProcessSheetToolFixtureDetailsVO, Long> {

	List<ProcessSheetToolFixtureDetailsVO> findByProcessSheetCompRoutingVO(ProcessSheetCompRoutingVO processSheetVO);

}
